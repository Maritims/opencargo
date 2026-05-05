package no.clueless.opencargo.product_selection.domain.service;

import no.clueless.opencargo.domain.model.Product;
import no.clueless.opencargo.domain.model.Products;
import no.clueless.opencargo.domain.model.applicability.EvaluationResult;
import no.clueless.opencargo.domain.model.applicability.Rejection;
import no.clueless.opencargo.product_selection.domain.model.ProductSelectionQuery;
import no.clueless.opencargo.product_selection.domain.model.Rule;
import no.clueless.opencargo.product_selection.port.in.RequestProductsCommand;
import no.clueless.opencargo.product_selection.port.in.RequestProductsUseCase;
import no.clueless.opencargo.product_selection.port.out.ProductRepository;
import no.clueless.opencargo.product_selection.port.out.RuleRepository;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;
import no.clueless.opencargo.domain.model.applicability.ApplicabilityReports;
import no.clueless.opencargo.shared.Population;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class RequestProductsService implements RequestProductsUseCase {
    private static final Logger            log = LoggerFactory.getLogger(RequestProductsService.class);
    private final        ProductRepository productRepository;
    private final        RuleRepository    ruleRepository;

    public RequestProductsService(ProductRepository productRepository, RuleRepository ruleRepository) {
        this.productRepository = ArgumentExceptionHelper.throwIfNull(productRepository, "productRepository");
        this.ruleRepository    = ArgumentExceptionHelper.throwIfNull(ruleRepository, "ruleRepository");
    }

    protected EvaluationResult evaluateProduct(RequestProductsCommand command, Product product, Set<Rule> rules) {
        if (command == null) {
            throw new IllegalArgumentException("command cannot be null");
        }
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if(rules == null || rules.isEmpty()) {
            throw new IllegalArgumentException("rules cannot be null or empty");
        }

        var query = new ProductSelectionQuery(command.getCargo(), command.getDestination());

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

    @Override
    public ApplicabilityReports<Product> requestProducts(RequestProductsCommand command) {
        ArgumentExceptionHelper.throwIfNull(command, "command");

        var applicableProducts = new HashSet<Product>();
        var products           = productRepository.getAll();
        var rejections         = new HashSet<Rejection<Product>>();
        var rules              = ruleRepository.getAll();

        for (Product product : products) {
            EvaluationResult evaluationResult = evaluateProduct(command, product, rules);
            if (evaluationResult.isSatisfied()) {
                applicableProducts.add(product);
            } else {
                rejections.add(new Rejection<>(product, evaluationResult.getReason()));
            }
        }


        return new ApplicabilityReports<>(applicableProducts.isEmpty() ? null : new Products(applicableProducts), rejections.isEmpty() ? null : Population.fromSetOf(rejections));
    }

    private static final class SingletonHolder {
        private static final RequestProductsService INSTANCE = new RequestProductsService(ProductRepository.create(), RuleRepository.create());
    }

    public static RequestProductsService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
