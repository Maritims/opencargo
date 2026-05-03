package no.clueless.opencargo.pricing.adapter.in.web;

import no.clueless.opencargo.pricing.port.in.RequestPricingCommand;
import no.clueless.opencargo.shared.DomainMapper;
import no.clueless.opencargo.shared.cargo.Cargo;
import no.clueless.opencargo.shared.geography.Address;
import no.clueless.opencargo.shared.geography.CountryCode;
import no.clueless.opencargo.shared.geography.PostalCode;

import java.util.Arrays;
import java.util.Currency;
import java.util.stream.Collectors;

public class RequestPricingWebCommand implements DomainMapper<RequestPricingCommand> {
    private double cargoWeight;
    private double cargoWidth;
    private double cargoLength;
    private double cargoHeight;
    private double cargoMonetaryValue;
    private int[]  productIds;
    private String destinationAddressLine1;
    private String destinationAddressLine2;
    private String destinationPostalCode;
    private String destinationCity;
    private String destinationState;
    private String destinationCountryCode;
    private String currency;

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    public double getCargoWidth() {
        return cargoWidth;
    }

    public void setCargoWidth(double cargoWidth) {
        this.cargoWidth = cargoWidth;
    }

    public double getCargoLength() {
        return cargoLength;
    }

    public void setCargoLength(double cargoLength) {
        this.cargoLength = cargoLength;
    }

    public double getCargoHeight() {
        return cargoHeight;
    }

    public void setCargoHeight(double cargoHeight) {
        this.cargoHeight = cargoHeight;
    }

    public double getCargoMonetaryValue() {
        return cargoMonetaryValue;
    }

    public void setCargoMonetaryValue(double cargoMonetaryValue) {
        this.cargoMonetaryValue = cargoMonetaryValue;
    }

    public int[] getProductIds() {
        return productIds;
    }

    public void setProductIds(int[] productIds) {
        this.productIds = productIds;
    }

    public String getDestinationAddressLine1() {
        return destinationAddressLine1;
    }

    public void setDestinationAddressLine1(String destinationAddressLine1) {
        this.destinationAddressLine1 = destinationAddressLine1;
    }

    public String getDestinationAddressLine2() {
        return destinationAddressLine2;
    }

    public void setDestinationAddressLine2(String destinationAddressLine2) {
        this.destinationAddressLine2 = destinationAddressLine2;
    }

    public String getDestinationPostalCode() {
        return destinationPostalCode;
    }

    public void setDestinationPostalCode(String destinationPostalCode) {
        this.destinationPostalCode = destinationPostalCode;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(String destinationState) {
        this.destinationState = destinationState;
    }

    public String getDestinationCountryCode() {
        return destinationCountryCode;
    }

    public void setDestinationCountryCode(String destinationCountryCode) {
        this.destinationCountryCode = destinationCountryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public RequestPricingCommand toDomain() {
        var cargo       = new Cargo(cargoWeight, cargoWidth, cargoLength, cargoHeight, cargoMonetaryValue);
        var productIds  = Arrays.stream(this.productIds).boxed().collect(Collectors.toSet());
        var destination = new Address(destinationAddressLine1, destinationAddressLine2, destinationCity, destinationState, new PostalCode(destinationPostalCode), new CountryCode(destinationCountryCode));
        var currency    = Currency.getInstance(this.currency);

        return new RequestPricingCommand(cargo, productIds, destination, currency);
    }
}
