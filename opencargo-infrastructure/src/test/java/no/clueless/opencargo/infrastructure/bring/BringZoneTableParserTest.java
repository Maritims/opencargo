package no.clueless.opencargo.infrastructure.bring;

import no.clueless.opencargo.domain.model.FreightTerminal;
import no.clueless.opencargo.domain.shared.PostalCode;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BringZoneTableParserTest {
    BringZoneTableParser sut;
    File                 mainZoneTableFile;
    File                 localZoneTableFile;

    @BeforeEach
    void setUp() {
        sut                = new BringZoneTableParser();
        mainZoneTableFile  = new File("src/test/resources/Hovedsonetabell-pakker_01122025.pdf");
        localZoneTableFile = new File("src/test/resources/Lokalsone-Pakke-til-bedrift-Pakke-til-hentested_01122025.pdf");
    }

    @Test
    void parseLocalZoneTable() {
        var localZoneTable   = sut.parseLocalZoneTable(localZoneTableFile);
        var freightTerminals = FreightTerminal.of(localZoneTable);

        for (var freightTerminal : freightTerminals) {
            for (var entry : freightTerminal.getZones().entrySet()) {
                System.out.printf("%s -> %s (inbound: %d, outbound: %d)%n", freightTerminal.getPostalCode(), entry.getKey(), entry.getValue().getInboundZones(), entry.getValue().getOutboundZones());
            }
        }
    }

    @Test
    void findMainZoneTableVerticalOffset() {
        try (var document = PDDocument.load(mainZoneTableFile)) {
            var actual = sut.findMainZoneTableVerticalOffset(document, "Oslo").orElseThrow();
            assertEquals(364.363, actual, 0.001);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void parseMainZoneTable() {
        var mainZoneTable = sut.parseMainZoneTable(mainZoneTableFile);
        System.out.println(mainZoneTable);
    }

    @Test
    void createFreightNetwork() {
        var actual = sut.createFreightNetwork(mainZoneTableFile, localZoneTableFile, PostalCode::new);
        System.out.println(actual);
    }

    @Test
    void example_from_the_bring_website() {
        var freightNetwork = sut.createFreightNetwork(mainZoneTableFile, localZoneTableFile, PostalCode::new);
        var actual         = freightNetwork.getTotalZonesBetween(new PostalCode("3110"), new PostalCode("6409"));
        assertEquals(5, actual);
    }
}