package no.clueless.opencargo.product_selection.domain.srevice.engine;

import no.clueless.opencargo.product_selection.domain.service.engine.ProductSelectionQuery;
import no.clueless.opencargo.shared.Population;
import no.clueless.opencargo.domain.model.geography.CountryCode;
import no.clueless.opencargo.domain.model.geography.PostalCode;
import no.clueless.opencargo.domain.model.geography.Address;
import no.clueless.opencargo.domain.model.Cargo;
import no.clueless.opencargo.domain.model.applicability.EvaluationResult;
import no.clueless.opencargo.domain.model.applicability.ApplicabilityReports;
import no.clueless.opencargo.domain.model.applicability.Rejection;
import no.clueless.opencargo.bindings.ProductListDTO;
import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.domain.model.Products;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;
import no.clueless.opencargo.product_selection.domain.service.engine.RuleEngine;
import no.clueless.opencargo.product_selection.domain.service.engine.Rule;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RuleEngineTest {

    @Test
    void throw_when_products_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new RuleEngine(null, null));
    }

    @Test
    void throw_when_rules_is_null() {
        assertThrows(IllegalArgumentException.class, () -> new RuleEngine(mock(), null));
    }

    @Test
    void do_not_throw_when_args_are_valid() {
        assertDoesNotThrow(() -> new RuleEngine(mock(), mock()));
    }

    @Test
    void evaluateProduct_should_throw_when_query_is_null() {
        var sut = new RuleEngine(mock(), mock());
        assertThrows(IllegalArgumentException.class, () -> sut.evaluateProduct(null, null));
    }

    @Test
    void evaluateProduct_should_throw_when_product_is_null() {
        var sut = new RuleEngine(mock(), mock());
        assertThrows(IllegalArgumentException.class, () -> sut.evaluateProduct(mock(ProductSelectionQuery.class), null));
    }

    @Test
    void evaluateProduct_should_always_consider_rules_without_product_id() {
        var product          = mock(Product.class);
        var evaluationResult = mock(EvaluationResult.class);

        when(evaluationResult.isSatisfied()).thenReturn(false);

        var query = mock(ProductSelectionQuery.class);
        var rule  = mock(Rule.class);

        when(rule.getId()).thenReturn(1);
        when(rule.getName()).thenReturn("Rule without product id");

        var rules = Population.fromSetOf(Set.of(rule));

        when(rule.evaluate(query)).thenReturn(evaluationResult);

        var sut = new RuleEngine(mock(), rules);

        var actual = sut.evaluateProduct(query, product);

        verify(rule).evaluate(query);
        assertEquals(evaluationResult, actual);
    }

    @Test
    void evaluateProduct_should_exclude_rule_when_product_is_not_null_and_does_not_match() {
        var productId = 1;
        var product   = mock(Product.class);
        when(product.getId()).thenReturn(productId);
        var products = Population.fromSetOf(Set.of(product));

        var evaluationResult = mock(EvaluationResult.class);
        when(evaluationResult.isSatisfied()).thenReturn(false);

        var query = mock(ProductSelectionQuery.class);
        var rule  = mock(Rule.class);
        when(rule.getProductIds()).thenReturn(Set.of(2));
        var rules = Population.fromSetOf(Set.of(rule));
        when(rule.evaluate(query)).thenReturn(evaluationResult);

        var sut = new RuleEngine(products, rules);

        var actual = sut.evaluateProduct(query, product);

        verify(rule, never()).evaluate(query);
        assertEquals(EvaluationResult.satisfied(), actual);
    }

    @Test
    void evaluateProduct_should_include_rule_when_product_is_not_null_and_does_match() {
        var productId = 1;
        var product   = mock(Product.class);
        when(product.getId()).thenReturn(productId);
        var products = Population.fromSetOf(Set.of(product));

        var evaluationResult = mock(EvaluationResult.class);
        when(evaluationResult.isSatisfied()).thenReturn(true);

        var query = mock(ProductSelectionQuery.class);
        var rule  = mock(Rule.class);
        when(rule.evaluate(query)).thenReturn(evaluationResult);
        when(rule.getProductIds()).thenReturn(Set.of(productId));
        var rules = Population.fromSetOf(Set.of(rule));

        var sut = new RuleEngine(products, rules);

        var actual = sut.evaluateProduct(query, product);

        verify(rule).evaluate(query);
        assertEquals(EvaluationResult.satisfied(), actual);
    }

    @Test
    void resolve_should_throw_when_query_is_null() {
        var sut = new RuleEngine(mock(), mock());
        assertThrows(IllegalArgumentException.class, () -> sut.resolve(null));
    }

    @Test
    void resolve_should_evaluate_all_products() {
        var product  = mock(Product.class);
        var products = Population.fromSetOf(Set.of(product));
        var sut      = spy(new RuleEngine(products, mock()));
        var query    = mock(ProductSelectionQuery.class);

        sut.resolve(query);

        verify(sut, times(products.size())).evaluateProduct(eq(query), any(Product.class));
    }

    @Test
    void resolve_should_consider_product_applicable_when_rule_is_satisfied() {
        var product  = mock(Product.class);
        var products = new Products(Set.of(product));
        var sut      = spy(new RuleEngine(products, mock()));
        var query    = mock(ProductSelectionQuery.class);
        var expected = new ApplicabilityReports<>(products, null);

        var actual = sut.resolve(query);

        assertEquals(expected, actual);
    }

    @Test
    void resolve_should_consider_product_rejected_when_rule_is_not_satisfied() {
        var product          = mock(Product.class);
        var products         = Population.fromSetOf(Set.of(product));
        var evaluationResult = mock(EvaluationResult.class);
        when(evaluationResult.isSatisfied()).thenReturn(false);
        when(evaluationResult.getReason()).thenReturn("foo bar");
        var rule = mock(Rule.class);
        when(rule.getId()).thenReturn(1);
        when(rule.getName()).thenReturn("Rule without product id");
        when(rule.evaluate(any(ProductSelectionQuery.class))).thenReturn(evaluationResult);
        var rules    = Population.fromSetOf(Set.of(rule));
        var sut      = spy(new RuleEngine(products, rules));
        var query    = mock(ProductSelectionQuery.class);
        var expected = new ApplicabilityReports<>(null, Population.fromSetOf(Set.of(new Rejection<>(product, "foo bar"))));

        var actual = sut.resolve(query);

        assertEquals(expected, actual);
    }

    @Test
    void engine_should_reject_due_to_lack_of_availability_in_sweden() {
        var sweden    = new CountryCode("se");
        var stockholm = new Address("Foo", "Bar", "Stockholm", null, new PostalCode("111 20"), sweden);
        var cargo     = new Cargo(10.0, 100.0, 50.0, 50.0, 1337.0);
        var query     = new ProductSelectionQuery(cargo, stockholm);

        var productListDTO = XmlMarshaller.unmarshalResourceSilently("products.xml", ProductListDTO.class);
        var products = productListDTO.getProduct()
                .stream()
                .map(productDTO -> new Product(productDTO.getId(), productDTO.getConsignorId(), productDTO.getNumber(), productDTO.getName()))
                .collect(Products.collector());
        var sut = RuleEngine.newBuilder()
                .withProductsFromResources()
                .withRulesFromResources()
                .build();
        var pakkeboks    = products.findByNumber("0344").orElseThrow(() -> new RuntimeException("A product with the number 0344 was not found"));
        var servicepakke = products.findByNumber("5800").orElseThrow(() -> new RuntimeException("A product with the number 5800 was not found"));
        var expected = new ApplicabilityReports<>(null, Population.fromSetOf(Set.of(
                new Rejection<>(servicepakke, "Service not available in country: SE"),
                new Rejection<>(pakkeboks, "Service not available in country: SE")
        )));

        var actual = sut.resolve(query);

        assertEquals(expected, actual);
    }

    @Test
    void engine_should_accept() {
        var norway = new CountryCode("no");
        var oslo   = new Address("Foo", "Bar", "Oslo", null, new PostalCode("1111"), norway);
        var cargo  = new Cargo(10.0, 100.0, 50.0, 50.0, 1337.0);
        var query  = new ProductSelectionQuery(cargo, oslo);

        var productListDTO = XmlMarshaller.unmarshalResourceSilently("products.xml", ProductListDTO.class);
        var products = productListDTO.getProduct()
                .stream()
                .map(productDTO -> new Product(productDTO.getId(), productDTO.getConsignorId(), productDTO.getNumber(), productDTO.getName()))
                .collect(Products.collector());
        var sut          = RuleEngine.newBuilder().withRulesFromResources().withProductsFromResources().build();
        var pakkeboks    = products.findByNumber("0344").orElseThrow(() -> new RuntimeException("A product with the number 0344 was not found"));
        var servicepakke = products.findByNumber("5800").orElseThrow(() -> new RuntimeException("A product with the number 5800 was not found"));
        var expected     = new ApplicabilityReports<>(new Products(pakkeboks, servicepakke), null);

        var actual = sut.resolve(query);

        assertEquals(expected, actual);
    }

    @Test
    void engine_should_reject_pakkeboks_due_to_weight_constraint() {
        var norway = new CountryCode("no");
        var oslo   = new Address("Foo", "Bar", "Oslo", null, new PostalCode("1111"), norway);
        var cargo  = new Cargo(11.0, 100.0, 50.0, 50.0, 1337.0);
        var query  = new ProductSelectionQuery(cargo, oslo);

        var productListDTO = XmlMarshaller.unmarshalResourceSilently("products.xml", ProductListDTO.class);
        var products = productListDTO.getProduct()
                .stream()
                .map(productDTO -> new Product(productDTO.getId(), productDTO.getConsignorId(), productDTO.getNumber(), productDTO.getName()))
                .collect(Products.collector());
        var sut          = RuleEngine.newBuilder().withRulesFromResources().withProductsFromResources().build();
        var pakkeboks    = products.findByNumber("0344").orElseThrow(() -> new RuntimeException("A product with the number 0344 was not found"));
        var servicepakke = products.findByNumber("5800").orElseThrow(() -> new RuntimeException("A product with the number 5800 was not found"));
        var expected     = new ApplicabilityReports<>(new Products(servicepakke), Population.fromSetOf(Set.of(new Rejection<>(pakkeboks, "The value 11.0 is greater than the maximum value of 10.0"))));

        var actual = sut.resolve(query);

        assertEquals(expected, actual);
    }

    @Test
    void return_unsatisfied_in_real_world_scenario_due_to_not_meeting_min_dimensions() {
        var norway = new CountryCode("no");
        var oslo   = new Address("foo", "Bar", "Oslo", null, new PostalCode("1100"), norway);
        var cargo  = new Cargo(10.0, 14.0, 10.0, 1.0, 1337.0);
        var query  = new ProductSelectionQuery(cargo, oslo);

        var productListDTO = XmlMarshaller.unmarshalResourceSilently("products.xml", ProductListDTO.class);
        var products = productListDTO.getProduct()
                .stream()
                .map(productDTO -> new Product(productDTO.getId(), productDTO.getConsignorId(), productDTO.getNumber(), productDTO.getName()))
                .collect(Products.collector());
        var sut          = RuleEngine.newBuilder().withProductsFromResources().withRulesFromResources().build();
        var pakkeboks    = products.findByNumber("0344").orElseThrow(() -> new RuntimeException("A product with the number 0344 was not found"));
        var servicepakke = products.findByNumber("5800").orElseThrow(() -> new RuntimeException("A product with the number 5800 was not found"));
        var expected = new ApplicabilityReports<>(null, Population.fromSetOf(Set.of(
                new Rejection<>(pakkeboks, "Package is too small on at least one axis"),
                new Rejection<>(servicepakke, "Package is too small on at least one axis")
        )));

        var actual = sut.resolve(query);

        assertEquals(expected, actual);
    }
}