package no.clueless.opencargo.infrastructure.persistence.bring;

import no.clueless.opencargo.domain.shared.PostalCode;
import no.clueless.opencargo.domain.shared.graph.UndirectedMatrix;
import no.clueless.opencargo.infrastructure.shared.OffsetAwarePDFTextStripper;
import no.clueless.opencargo.infrastructure.shared.PdfVerticalOffsetFinder;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class BringMainZoneTableParser {
    private final PdfVerticalOffsetFinder pdfVerticalOffsetFinder;

    public BringMainZoneTableParser(PdfVerticalOffsetFinder pdfVerticalOffsetFinder) {
        this.pdfVerticalOffsetFinder = Objects.requireNonNull(pdfVerticalOffsetFinder, "pdfVerticalOffsetFinder cannot be null");
    }

    /**
     * Parse the main zone table from the PDF.
     *
     * @param file The PDF file to parse.
     * @return The main zone table as a string. Each line starts with a postal code, followed by a city name and a list of zones. A space separates each zone.
     * When considering only the zone columns, each zone column describes the number of zones between the postal code of the current row and the postal code of the row index equal to the zone column index.
     */
    public UndirectedMatrix<PostalCode> parseMainZoneTable(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        try (var document = PDDocument.load(file)) {
            var stripper = pdfVerticalOffsetFinder.findVerticalOffset(document, "Oslo")
                    .map(OffsetAwarePDFTextStripper::forVerticalOffset)
                    .orElseThrow(() -> new RuntimeException("Failed to find vertical offset based on needle 'Oslo'"));
            var text = stripper.getText(document).trim();
            var rows = text.split("\n");

            var lines        = new ArrayList<>(List.of("")); // Placeholder line for the header tokens.
            var headerTokens = new ArrayList<>(List.of("PostalCode"));
            var pattern      = Pattern.compile("^(\\d{4})\\s+(.+?)\\s+((?:\\d+\\s*)+)$");
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var matcher           = pattern.matcher(row);
                if (!matcher.find()) {
                    throw new IllegalStateException("Unexpected format at row " + i + ": " + row);
                }

                var postalCode = matcher.group(1);
                var zones      = matcher.group(3).replace(" ", ",");
                var line       = String.format("%s,%s", postalCode, zones);

                headerTokens.add(postalCode);
                lines.add(line);
            }

            lines.add(0, String.join(",", headerTokens));

            return UndirectedMatrix.fromLines(lines, PostalCode::new, true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create PDFTextStripper", e);
        }
    }
}
