package no.clueless.opencargo.domain.shared;

/**
 * An implementation of ISO 780:2015.
 */
public enum HandlingDirective {
    FRAGILE(3, "Fragile, handle with care", "Contents of the distribution package are fragile therefore handle with care", "7000-0621"),
    THIS_WAY_UP(13, "This way up", "This is the correct upright position of the distribution packages for transport and/or storage", "7000-0623"),
    TEMPERATURE_LIMITS(14, "Temperature limits", "Distribution packages shall be stored, transported and handled within temperature limits indicated", "7000-0632"),
    STACKING_LIMIT_BY_MASS(15, "Stacking limit by mass", "Maximum stacking load which may be stacked on the distribution package", "7000-0630"),
    STACKING_LIMIT_BY_NUMBER(16, "Stacking limit by number", "Maximum number of identical transport packages/items which may be stacked on the bottom package, where 'n' is the limiting number", "7000-2403"),
    DO_NOT_STACK(17, "Do not stack", "Stacking of the distribution packages is not allowed and no load shall be placed on the distribution packages", "7000-2402");

    private final int    symbolNumber;
    private final String title;
    private final String meaning;
    private final String isoRegistryId;

    HandlingDirective(int symbolNumber, String title, String meaning, String isoRegistryId) {
        this.symbolNumber  = symbolNumber;
        this.title         = title;
        this.meaning       = meaning;
        this.isoRegistryId = isoRegistryId;
    }

    public int getSymbolNumber() {
        return symbolNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getIsoRegistryId() {
        return isoRegistryId;
    }
}
