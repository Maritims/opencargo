package no.clueless.opencargo;

import no.clueless.opencargo.applicability.ProductApplicabilityReport;
import no.clueless.opencargo.applicability.Rejection;
import no.clueless.opencargo.applicability.Rejections;
import no.clueless.opencargo.domain.Product;
import no.clueless.opencargo.domain.Products;
import no.clueless.opencargo.rules.EvaluationResult;
import no.clueless.opencargo.rules.Rules;
import no.clueless.opencargo.util.ArgumentExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public final class RuleEngine {
    private static final Logger   log = LoggerFactory.getLogger(RuleEngine.class);
    private final        Products products;
    private final        Rules    rules;

    public RuleEngine(Products products, Rules rules) {
        this.products = ArgumentExceptionHelper.throwIfNull(products, "products");
        this.rules    = ArgumentExceptionHelper.throwIfNull(rules, "rules");
    }

    public EvaluationResult evaluateProduct(Query query, Product product) {
        ArgumentExceptionHelper.throwIfNull(query, "query");
        ArgumentExceptionHelper.throwIfNull(product, "product");

        return rules.stream()
                .peek(rule -> {
                    if (rule.getProductIds() == null || rule.getProductIds().isEmpty()) {
                        log.warn("Rule id {} ({}) is not restricted to any product id and will affect every product", rule.getId(), rule.getName());
                    }
                })
                .filter(rule -> rule.getProductIds() == null || rule.getProductIds().isEmpty() || rule.getProductIds().contains(product.getId()))
                .map(rule -> rule.evaluate(query))
                .filter(evaluationResult -> !evaluationResult.isSatisfied())
                .findFirst()
                .orElse(EvaluationResult.satisfied());
    }

    public ProductApplicabilityReport resolve(Query query) {
        ArgumentExceptionHelper.throwIfNull(query, "query");
        Set<Product>   applicableProducts = new HashSet<>();
        Set<Rejection> rejections         = new HashSet<>();

        for (Product product : products) {
            EvaluationResult evaluationResult = evaluateProduct(query, product);
            if (evaluationResult.isSatisfied()) {
                applicableProducts.add(product);
            } else {
                rejections.add(new Rejection(product, evaluationResult.getReason()));
            }
        }

        return new ProductApplicabilityReport(applicableProducts.isEmpty() ? null : new Products(applicableProducts), rejections.isEmpty() ? null : new Rejections(rejections));
    }
}
