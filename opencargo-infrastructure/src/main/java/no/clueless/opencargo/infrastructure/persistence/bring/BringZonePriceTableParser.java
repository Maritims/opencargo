package no.clueless.opencargo.infrastructure.persistence.bring;

import no.clueless.opencargo.domain.model.FreightZonePrice;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.physical.WeightUnit;
import no.clueless.opencargo.domain.shared.Money;
import no.clueless.opencargo.infrastructure.shared.OffsetAwarePDFTextStripper;
import no.clueless.opencargo.infrastructure.shared.PdfVerticalOffsetFinder;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BringZonePriceTableParser {
    private static final Pattern                 ZONE_PRICE_PATTERN = Pattern.compile("^(\\b\\d+\\b\\s*){9}$");
    private final        PdfVerticalOffsetFinder pdfVerticalOffsetFinder;

    public BringZonePriceTableParser(PdfVerticalOffsetFinder pdfVerticalOffsetFinder) {
        this.pdfVerticalOffsetFinder = Objects.requireNonNull(pdfVerticalOffsetFinder, "pdfVerticalOffsetFinder cannot be null");
    }

    public Set<FreightZonePrice> parseZonePriceTable(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }
        if (!file.getName().endsWith(".pdf")) {
            throw new IllegalArgumentException("file " + file.getName() + " must be a PDF");
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("file " + file.getName() + " does not exist");
        }

        String text;
        try (var document = PDDocument.load(file)) {
            var stripper = pdfVerticalOffsetFinder.findVerticalOffset(document, "Sone")
                    .map(OffsetAwarePDFTextStripper::forVerticalOffset)
                    .orElseThrow(() -> new RuntimeException("Failed to find vertical offset based on needle 'Sone' in " + file.getName()));
            text           = stripper.getText(document).trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load PDF", e);
        }
        var lines = text.split("\n");
        var zonePrices = new HashSet<FreightZonePrice>();

        for (var line : lines) {
            var matcher = ZONE_PRICE_PATTERN.matcher(line);
            if (!matcher.matches()) {
                continue;
            }

            var ints = line.split(" ");
            var maxWeight = new Weight(new BigDecimal(ints[0]), WeightUnit.KILOGRAM);
            var prices = new HashMap<Integer, Money>();
            for(var i = 1; i < ints.length; i++) {
                var price = new Money(new BigDecimal(ints[i]), Currency.getInstance("NOK"));
                prices.put(i, price);
            }
            var freightZonePrice = new FreightZonePrice(maxWeight, prices);

            zonePrices.add(freightZonePrice);
        }

        return zonePrices.stream()
                .sorted(Comparator.comparing(FreightZonePrice::getMaxWeight))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
