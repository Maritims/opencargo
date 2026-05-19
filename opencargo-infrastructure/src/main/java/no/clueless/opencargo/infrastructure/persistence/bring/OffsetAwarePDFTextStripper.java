package no.clueless.opencargo.infrastructure.persistence.bring;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class OffsetAwarePDFTextStripper extends PDFTextStripper {
    private final float verticalOffset;

    /**
     * Instantiate a new PDFTextStripper object.
     *
     * @throws IOException If there is an error loading the properties.
     */
    public OffsetAwarePDFTextStripper(float verticalOffset) throws IOException {
        if (verticalOffset < 0) {
            throw new IllegalArgumentException("verticalOffset must be greater than or equal to 0");
        }
        this.verticalOffset = verticalOffset;
    }

    public static OffsetAwarePDFTextStripper forVerticalOffset(float verticalOffset) {
        try {
            return new OffsetAwarePDFTextStripper(verticalOffset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
}
