package no.clueless.opencargo.infrastructure.persistence.bring;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PdfVerticalOffsetFinderTest {
    PdfVerticalOffsetFinder sut;

    @BeforeEach
    void setUp() {
        sut = new PdfVerticalOffsetFinder();
    }

    @Test
    void findVerticalOffset() {
        var file = new File("src/test/resources/Hovedsonetabell-pakker_01122025.pdf");
        try (var document = PDDocument.load(file)) {
            var actual = sut.findVerticalOffset(document, "Oslo").orElseThrow();
            assertEquals(364.363, actual, 0.001);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}