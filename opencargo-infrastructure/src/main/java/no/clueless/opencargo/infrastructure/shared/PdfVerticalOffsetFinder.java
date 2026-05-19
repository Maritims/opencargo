package no.clueless.opencargo.infrastructure.shared;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class PdfVerticalOffsetFinder {
    /**
     * Find the vertical offset of the first row in the PDF.
     *
     * @param haystack The document to search in.
     * @param needle   The string to search for. The latest occurrence of this string indicates the start of the row.
     */
    public Optional<Float> findVerticalOffset(PDDocument haystack, String needle) throws IOException {
        if (haystack == null) {
            throw new IllegalArgumentException("haystack cannot be null");
        }

        var stripper = new PDFTextStripper() {
            final StringBuilder     stringBuilder     = new StringBuilder();
            final LinkedList<Float> identifiedOffsets = new LinkedList<>();

            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                if (stringBuilder.toString().contains(needle)) {
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
}
