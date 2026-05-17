package no.clueless.opencargo.infrastructure.bring;

import no.clueless.opencargo.domain.model.LocalZone;
import no.clueless.opencargo.domain.shared.PostalCode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class BringPdfParser {
    private static final Pattern PATTERN = Pattern.compile("^(\\d{4})\\s(\\d{4})\\s(\\d)\\s(\\d)$");

    String extractText(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        PDDocument document;
        try {
            document = PDDocument.load(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load PDF", e);
        }

        PDFTextStripper stripper;
        try {
            stripper = new PDFTextStripper();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create PDFTextStripper", e);
        }

        String text;
        try {
            text = stripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract text from PDF", e);
        }

        try {
            document.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close PDF document", e);
        }

        return text;
    }

    public String parseMainZoneTable(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        var text = extractText(file);
        return text;
    }

    public LinkedHashMap<PostalCode, LinkedHashSet<LocalZone>> parseLocalZoneTable(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        var text       = extractText(file);
        var lines      = text.split("\n");
        var localZones = new LinkedHashMap<PostalCode, LinkedHashSet<LocalZone>>();

        for (var line : lines) {
            var matcher = PATTERN.matcher(line);
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

        return localZones;
    }
}
