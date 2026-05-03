package no.clueless.opencargo.infrastructure.web;

public class ProductQueryRequest {
    private double cargoWeight;
    private double cargoWidth;
    private double cargoLength;
    private double cargoHeight;
    private double cargoMonetaryValue;

    private String deliveryAddressLine1;
    private String deliveryAddressLine2;
    private String deliveryPostalCode;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryCountryCode;

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

    public String getDeliveryAddressLine1() {
        return deliveryAddressLine1;
    }

    public void setDeliveryAddressLine1(String deliveryAddressLine1) {
        this.deliveryAddressLine1 = deliveryAddressLine1;
    }

    public String getDeliveryAddressLine2() {
        return deliveryAddressLine2;
    }

    public void setDeliveryAddressLine2(String deliveryAddressLine2) {
        this.deliveryAddressLine2 = deliveryAddressLine2;
    }

    public String getDeliveryPostalCode() {
        return deliveryPostalCode;
    }

    public void setDeliveryPostalCode(String deliveryPostalCode) {
        this.deliveryPostalCode = deliveryPostalCode;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryCountryCode() {
        return deliveryCountryCode;
    }

    public void setDeliveryCountryCode(String deliveryCountryCode) {
        this.deliveryCountryCode = deliveryCountryCode;
    }
}
