package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.criteria.Constraint;
import no.clueless.opencargo.domain.shared.Money;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreightPrice {
    private final Money                   basePrice;
    private final List<FreightSurcharge>  surcharges;
    private final Map<String, Constraint> constraints;

    public FreightPrice(Money basePrice, List<FreightSurcharge> surcharges, List<Constraint> constraints) {
        this.basePrice   = Objects.requireNonNull(basePrice, "basePrice cannot be null");
        this.surcharges  = surcharges == null ? List.of() : List.copyOf(surcharges);
        this.constraints = constraints == null ? Map.of() : constraints.stream().collect(Collectors.toMap(
                (Constraint constraint) -> {
                    var key = constraint.getClass().getSimpleName().replace("Constraint", "");
                    key = key.substring(0, 1).toLowerCase() + key.substring(1);
                    return key;
                },
                entry -> entry
        ));
    }

    public Money getBasePrice() {
        return basePrice;
    }

    public List<FreightSurcharge> getSurcharges() {
        return surcharges;
    }

    public Map<String, Constraint> getConstraints() {
        return constraints;
    }

    public Money getTotalPrice(Parcel parcel) {
        if (parcel == null) {
            throw new IllegalArgumentException("parcel cannot be null");
        }
        var totalPrice = basePrice;

        for(var surcharge : surcharges) {
            if (surcharge.isApplicable(parcel)) {
                totalPrice = surcharge.getModifier().applyTo(totalPrice);
            }
        }

        return totalPrice;
    }
}
