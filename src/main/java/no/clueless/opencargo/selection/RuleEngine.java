package no.clueless.opencargo.selection;

import no.clueless.opencargo.applicability.EvaluationResult;
import no.clueless.opencargo.ProductQuery;
import no.clueless.opencargo.applicability.ApplicabilityReports;
import no.clueless.opencargo.applicability.Rejection;
import no.clueless.opencargo.applicability.Rejections;
import no.clueless.opencargo.domain.product.Product;
import no.clueless.opencargo.domain.product.Products;
import no.clueless.opencargo.infrastructure.ArgumentExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

public final class RuleEngine {
    private static final Logger   log = LoggerFactory.getLogger(RuleEngine.class);
    private final Products       products;
    private final SelectionRules rules;

    public RuleEngine(Products products, SelectionRules rules) {
        this.products = ArgumentExceptionHelper.throwIfNull(products, "products");
        this.rules    = ArgumentExceptionHelper.throwIfNull(rules, "rules");
    }

    public EvaluationResult evaluateProduct(ProductQuery productQuery, Product product) {
        ArgumentExceptionHelper.throwIfNull(productQuery, "query");
        ArgumentExceptionHelper.throwIfNull(product, "product");

        return rules.stream()
                .peek(rule -> {
                    if (rule.getProductIds() == null || rule.getProductIds().isEmpty()) {
                        log.warn("Rule id {} ({}) is not restricted to any product id and will affect every product", rule.getId(), rule.getName());
                    }
                })
                .filter(rule -> rule.getProductIds() == null || rule.getProductIds().isEmpty() || rule.getProductIds().contains(product.getId()))
                .map(rule -> rule.evaluate(productQuery))
                .filter(evaluationResult -> !evaluationResult.isSatisfied())
                .findFirst()
                .orElse(EvaluationResult.satisfied());
    }

    public ApplicabilityReports<Product> resolve(ProductQuery productQuery) {
        ArgumentExceptionHelper.throwIfNull(productQuery, "query");
        var applicableProducts = new HashSet<Product>();
        var rejections         = new HashSet<Rejection<Product>>();

        for (Product product : products) {
            EvaluationResult evaluationResult = evaluateProduct(productQuery, product);
            if (evaluationResult.isSatisfied()) {
                applicableProducts.add(product);
            } else {
                rejections.add(new Rejection<>(product, evaluationResult.getReason()));
            }
        }

        return new ApplicabilityReports<>(applicableProducts.isEmpty() ? null : new Products(applicableProducts), rejections.isEmpty() ? null : new Rejections<>(rejections));
    }
}
