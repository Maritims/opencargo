package no.clueless.opencargo.infrastructure.persistence.bring;

import no.clueless.opencargo.domain.model.FreightTerminal;
import no.clueless.opencargo.domain.model.LocalZone;
import no.clueless.opencargo.domain.shared.PostalCode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BringLocalZoneTableParser {
    private static final Pattern LOCAL_ZONE_PATTERN = Pattern.compile("^(\\d{4})\\s(\\d{4})\\s(\\d)\\s(\\d)$");

    public Map<PostalCode, FreightTerminal> parseLocalZoneTable(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        String text;
        try (var document = PDDocument.load(file)) {
            var stripper = new PDFTextStripper();
            text = stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load PDF", e);
        }
        var lines      = text.split("\n");
        var localZones = new LinkedHashMap<PostalCode, LinkedHashSet<LocalZone>>();

        for (var line : lines) {
            var matcher = LOCAL_ZONE_PATTERN.matcher(line);
            if (!matcher.matches()) {
                continue;
            }

            var terminalPostCode = new PostalCode(matcher.group(2));
            localZones.computeIfAbsent(terminalPostCode, k -> new LinkedHashSet<>())
                    .add(new LocalZone(new PostalCode(
                            matcher.group(1)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4))
                    ));
        }

        return localZones.entrySet()
                .stream()
                .map(entry -> new FreightTerminal(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(FreightTerminal::getPostalCode, freightTerminal -> freightTerminal));
    }
}
