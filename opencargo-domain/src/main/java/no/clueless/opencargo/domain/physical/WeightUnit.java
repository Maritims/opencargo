package no.clueless.opencargo.domain.physical;

public enum WeightUnit {
    KILOGRAM("KG"),
    GRAM("G");

    private final String symbol;

    WeightUnit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
