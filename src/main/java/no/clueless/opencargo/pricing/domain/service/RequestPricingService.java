package no.clueless.opencargo.pricing.domain.service;

import no.clueless.opencargo.pricing.domain.model.breakdown.PriceBreakdown;
import no.clueless.opencargo.pricing.domain.model.breakdown.PriceComponent;
import no.clueless.opencargo.pricing.domain.model.breakdown.PriceComponentType;
import no.clueless.opencargo.pricing.domain.model.policy.PricingPolicy;
import no.clueless.opencargo.pricing.domain.model.PricingQuery;
import no.clueless.opencargo.pricing.port.in.RequestPricingCommand;
import no.clueless.opencargo.pricing.port.in.RequestPricingUseCase;
import no.clueless.opencargo.pricing.port.out.PolicyRepository;
import no.clueless.opencargo.shared.ArgumentExceptionHelper;

import java.util.Comparator;
import java.util.stream.Stream;

public class RequestPricingService implements RequestPricingUseCase {
    private final PolicyRepository policyRepository;

    public RequestPricingService(PolicyRepository policyRepository) {
        this.policyRepository = ArgumentExceptionHelper.throwIfNull(policyRepository, "policyRepository");
    }

    @Override
    public PriceBreakdown requestPricing(RequestPricingCommand command) {
        ArgumentExceptionHelper.throwIfNull(command, "command");

        var bestPolicy = policyRepository.getAll()
                .stream()
                .filter(policy -> policy.resolve(new PricingQuery(
                        command.getCargo(),
                        command.getProductIds(),
                        command.getDestination(),
                        command.getCurrency()
                )).isApplicable())
                .max(Comparator.comparingInt(PricingPolicy::getPriority))
                .orElseThrow(() -> new RuntimeException("No applicable policies found"));

        var baseComponent = new PriceComponent(bestPolicy.getName(), bestPolicy.getBasePrice(), PriceComponentType.BASE);
        var query = new PricingQuery(command.getCargo(), command.getProductIds(), command.getDestination(), command.getCurrency());

        if (bestPolicy.getPriceModifiers() != null) {
            var modifierStream = bestPolicy.getPriceModifiers()
                    .stream()
                    .filter(modifier -> modifier.isApplicable(query))
                    .map(modifier -> new PriceComponent(
                            modifier.getName(),
                            modifier.calculateDelta(query, baseComponent.getAmount()),
                            PriceComponentType.SURCHARGE
                    ));

            return Stream.concat(Stream.of(baseComponent), modifierStream).collect(PriceBreakdown.collector(query.getCurrency()));
        } else {
            return Stream.of(baseComponent).collect(PriceBreakdown.collector(query.getCurrency()));
        }
    }

    private static final class SingletonHolder {
        private static final RequestPricingService INSTANCE = (RequestPricingService) RequestPricingUseCase.create();
    }

    public static RequestPricingService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
