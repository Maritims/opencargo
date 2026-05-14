package no.clueless.opencargo.domain.shared;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public enum AdrClass {
    NONE("0"),
    CLASS_1_EXPLOSIVES("1"),
    CLASS_2_GASES("2"),
    CLASS_3_FLAMMABLE_LIQUIDS("3"),
    CLASS_4_1_FLAMMABLE_SOLIDS("4.1"),
    CLASS_4_2_SPONTANEOUS_COMBUSTION("4.2"),
    CLASS_4_3_DANGEROUS_WHEN_WET("4.3"),
    CLASS_5_1_OXIDIZING_SUBSTANCES("5.1"),
    CLASS_5_2_ORGANIC_PEROXIDES("5.2"),
    CLASS_6_1_TOXIC_SUBSTANCES("6.1"),
    CLASS_6_2_INFECTIOUS_SUBSTANCES("6.2"),
    CLASS_7_RADIOACTIVE_MATERIAL("7"),
    CLASS_8_CORROSIVE_SUBSTANCES("8"),
    CLASS_9_MISCELLANEOUS("9");

    private static final Set<AdrClass> ALL_CLASSES = Set.of(values());
    private final        String        shortCode;

    AdrClass(String shortCode) {
        this.shortCode = shortCode;
    }

    public static Optional<AdrClass> fromShortCode(String shortCode) {
        if (shortCode == null || shortCode.isBlank()) {
            throw new IllegalArgumentException("shortCode cannot be null or empty");
        }
        return ALL_CLASSES.stream()
                .filter(c -> c.shortCode.equals(shortCode))
                .findFirst();
    }

    public static Set<AdrClass> fromShortCodes(List<String> shortCodes) {
        if (shortCodes == null || shortCodes.isEmpty()) {
            throw new IllegalArgumentException("shortCodes cannot be null or empty");
        }
        return shortCodes.stream()
                .flatMap(shortCode -> fromShortCode(shortCode).stream())
                .collect(Collectors.toSet());
    }
}
