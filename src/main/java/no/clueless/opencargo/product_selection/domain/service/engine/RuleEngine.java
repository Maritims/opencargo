package no.clueless.opencargo.product_selection.domain.service.engine;

import no.clueless.opencargo.domain.model.applicability.EvaluationResult;
import no.clueless.opencargo.domain.model.applicability.ApplicabilityReports;
import no.clueless.opencargo.domain.model.applicability.Rejection;
import no.clueless.opencargo.bindings.ProductListDTO;
import no.clueless.opencargo.bindings.RuleListDTO;
import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.domain.model.Products;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.shared.Population;
import no.clueless.opencargo.infrastructure.marshalling.XmlMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public final class RuleEngine {
    private static final Logger                            log = LoggerFactory.getLogger(RuleEngine.class);
    private final        Population<Product, Set<Product>> products;
    private final        Population<Rule, Set<Rule>>       rules;

    public RuleEngine(Population<Product, Set<Product>> products, Population<Rule, Set<Rule>> rules) {
        this.products = ArgumentExceptionHelper.throwIfNull(products, "products");
        this.rules    = ArgumentExceptionHelper.throwIfNull(rules, "rules");
    }

    public EvaluationResult evaluateProduct(ProductSelectionQuery productSelectionQuery, Product product) {
        ArgumentExceptionHelper.throwIfNull(productSelectionQuery, "query");
        ArgumentExceptionHelper.throwIfNull(product, "product");

        return rules.stream()
                .peek(rule -> {
                    if (rule.getProductIds() == null || rule.getProductIds().isEmpty()) {
                        log.warn("Rule id {} ({}) is not restricted to any product id and will affect every product", rule.getId(), rule.getName());
                    }
                })
                .filter(rule -> rule.getProductIds() == null || rule.getProductIds().isEmpty() || rule.getProductIds().contains(product.getId()))
                .map(rule -> rule.evaluate(productSelectionQuery))
                .filter(evaluationResult -> !evaluationResult.isSatisfied())
                .findFirst()
                .orElse(EvaluationResult.satisfied());
    }

    public ApplicabilityReports<Product> resolve(ProductSelectionQuery productSelectionQuery) {
        ArgumentExceptionHelper.throwIfNull(productSelectionQuery, "query");
        var applicableProducts = new HashSet<Product>();
        var rejections         = new HashSet<Rejection<Product>>();

        for (Product product : products) {
            EvaluationResult evaluationResult = evaluateProduct(productSelectionQuery, product);
            if (evaluationResult.isSatisfied()) {
                applicableProducts.add(product);
            } else {
                rejections.add(new Rejection<>(product, evaluationResult.getReason()));
            }
        }

        return new ApplicabilityReports<>(applicableProducts.isEmpty() ? null : new Products(applicableProducts), rejections.isEmpty() ? null : Population.fromSetOf(rejections));
    }

    public static class Builder {
        private Population<Product, Set<Product>> products;
        private Population<Rule, Set<Rule>>       rules;

        private Builder() {
        }

        public Builder withProducts(Population<Product, Set<Product>> products) {
            this.products = ArgumentExceptionHelper.throwIfNull(products, "products");
            return this;
        }

        public Builder withRules(Population<Rule, Set<Rule>> rules) {
            this.rules = ArgumentExceptionHelper.throwIfNull(rules, "rules");
            return this;
        }

        public Builder withProductsFromResources(String filename) {
            ArgumentExceptionHelper.throwIfNullOrBlank(filename, "filename");

            var productListDTO = XmlMarshaller.unmarshalResourceSilently(filename, ProductListDTO.class);
            var products = productListDTO.getProduct()
                    .stream()
                    .map(productDTO -> new Product(productDTO.getId(), productDTO.getConsignorId(), productDTO.getNumber(), productDTO.getName()))
                    .collect(Population.collector(() -> (Set<Product>) new HashSet<Product>()));

            return withProducts(products);
        }

        public Builder withProductsFromResources() {
            return withProductsFromResources("products.xml");
        }

        public Builder withRulesFromResources(String filename) {
            ArgumentExceptionHelper.throwIfNullOrBlank(filename, "filename");

            var ruleListDTO = XmlMarshaller.unmarshalResourceSilently(filename, RuleListDTO.class);
            var rules = RulesMapper.getInstance().mapToRules(ruleListDTO);

            return withRules(rules);
        }

        public Builder withRulesFromResources() {
            return withRulesFromResources("rules.xml");
        }

        public RuleEngine build() {
            return new RuleEngine(products, rules);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private static final class SingletonHolder {
        private static final RuleEngine INSTANCE = RuleEngine.newBuilder()
                .withRulesFromResources()
                .withProductsFromResources()
                .build();
    }

    public static RuleEngine getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
