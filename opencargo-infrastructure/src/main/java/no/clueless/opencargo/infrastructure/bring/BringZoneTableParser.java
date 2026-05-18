package no.clueless.opencargo.infrastructure.bring;

import no.clueless.opencargo.domain.model.FreightNetwork;
import no.clueless.opencargo.domain.model.FreightTerminal;
import no.clueless.opencargo.domain.model.LocalZone;
import no.clueless.opencargo.domain.shared.PostalCode;
import no.clueless.opencargo.domain.shared.graph.UndirectedMatrix;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BringZoneTableParser {
    private static final Pattern LOCAL_ZONE_PATTERN = Pattern.compile("^(\\d{4})\\s(\\d{4})\\s(\\d)\\s(\\d)$");

    protected LinkedHashMap<PostalCode, LinkedHashSet<LocalZone>> parseLocalZoneTable(File file) {
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

        return localZones;
    }

    /**
     * Find the vertical offset of the first row in the PDF.
     *
     * @param haystack The document to search in.
     * @param needle   The string to search for. The latest occurrence of this string indicates the start of the row.
     */
    protected Optional<Float> findMainZoneTableVerticalOffset(PDDocument haystack, String needle) throws IOException {
        if (haystack == null) {
            throw new IllegalArgumentException("haystack cannot be null");
        }

        var stripper = new PDFTextStripper() {
            final StringBuilder     stringBuilder     = new StringBuilder();
            final LinkedList<Float> identifiedOffsets = new LinkedList<>();

            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                if (stringBuilder.toString().endsWith(needle)) {
                    identifiedOffsets.add(getCurrentPage().getMediaBox().getHeight() - textPositions.get(0).getTextMatrix().getTranslateY());
                    stringBuilder.setLength(0);
                }
                stringBuilder.append(text);
                super.writeString(text, textPositions);
            }
        };

        // Trigger the PDFTextStripper to start parsing the PDF.
        stripper.getText(haystack);

        return stripper.identifiedOffsets.isEmpty() ? Optional.empty() : Optional.of(stripper.identifiedOffsets.getLast());
    }

    /**
     * Parse the main zone table from the PDF.
     *
     * @param file The PDF file to parse.
     * @return The main zone table as a string. Each line starts with a postal code, followed by a city name and a list of zones. A space separates each zone.
     * When considering only the zone columns, each zone column describes the number of zones between the postal code of the current row and the postal code of the row index equal to the zone column index.
     */
    protected String parseMainZoneTable(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file cannot be null");
        }

        try (var document = PDDocument.load(file)) {
            var verticalOffset = findMainZoneTableVerticalOffset(document, "Oslo").orElseThrow(() -> new RuntimeException("Failed to find vertical offset based on needle 'Oslo'"));
            var parser = new PDFTextStripper() {
                @Override
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                    if (textPositions != null && !textPositions.isEmpty()) {
                        var firstPosition    = textPositions.get(0);
                        var pageHeight       = getCurrentPage().getMediaBox().getHeight();
                        var rawY             = firstPosition.getTextMatrix().getTranslateY();
                        var absoluteYFromTop = pageHeight - rawY;

                        if (absoluteYFromTop < verticalOffset) {
                            return;
                        }
                    }
                    super.writeString(text, textPositions);
                }
            };
            return parser.getText(document).trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create PDFTextStripper", e);
        }
    }

    /**
     * Create a freight network from the main zone table and local zone table.
     *
     * @param mainZoneTableFile  The PDF file containing the main zone table.
     * @param localZoneTableFile The PDF file containing the local zone table.
     * @param vertexMapper       A function that maps postal codes to vertices in the undirected graph.
     * @return A FreightNetwork object containing the main zone table, local zone table, and the undirected graph.
     */
    public FreightNetwork createFreightNetwork(File mainZoneTableFile, File localZoneTableFile, Function<String, PostalCode> vertexMapper) {
        if (mainZoneTableFile == null) {
            throw new IllegalArgumentException("mainZoneTableFile cannot be null");
        }
        if (localZoneTableFile == null) {
            throw new IllegalArgumentException("localZoneTableFile cannot be null");
        }

        var mainZoneTable      = parseMainZoneTable(mainZoneTableFile);
        var mainZoneTableLines = mainZoneTable.split("\n");
        var localZoneTable     = parseLocalZoneTable(localZoneTableFile);
        var lines              = new ArrayList<>(List.of("")); // Placeholder line for the header tokens.
        var headerTokens       = new ArrayList<>(List.of("PostalCode"));
        var pattern            = Pattern.compile("^(\\d{4})\\s+(.+?)\\s+((?:\\d+\\s*)+)$");

        for (var i = 0; i < mainZoneTableLines.length; i++) {
            var mainZoneTableLine = mainZoneTableLines[i];
            var matcher           = pattern.matcher(mainZoneTableLine);
            if (!matcher.find()) {
                throw new IllegalStateException("Unexpected format at line " + i + ": " + mainZoneTableLine);
            }

            var postalCode = matcher.group(1);
            var zones      = matcher.group(3).replace(" ", ",");
            var line       = String.format("%s,%s", postalCode, zones);

            headerTokens.add(postalCode);
            lines.add(line);
        }

        lines.add(0, String.join(",", headerTokens));

        var undirectedMatrix = UndirectedMatrix.fromLines(lines, vertexMapper, true);
        var freightTerminals = localZoneTable.entrySet()
                .stream()
                .map(entry -> new FreightTerminal(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(FreightTerminal::getPostalCode, Function.identity()));

        return new FreightNetwork(undirectedMatrix, freightTerminals);
    }
}
