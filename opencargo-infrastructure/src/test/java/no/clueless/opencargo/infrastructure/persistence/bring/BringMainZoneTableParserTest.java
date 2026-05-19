package no.clueless.opencargo.infrastructure.persistence.bring;

import no.clueless.opencargo.infrastructure.shared.PdfVerticalOffsetFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class BringMainZoneTableParserTest {
    BringMainZoneTableParser sut;
    File                     file;

    @BeforeEach
    void setUp() {
        sut  = new BringMainZoneTableParser(new PdfVerticalOffsetFinder());
        file = new File("src/test/resources/Hovedsonetabell-pakker_01122025.pdf");
    }

    @Test
    void parseMainZoneTable() {
        var mainZoneTable = sut.parseMainZoneTable(file);
        System.out.println(mainZoneTable);
    }
}