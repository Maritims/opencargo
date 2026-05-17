package no.clueless.opencargo.infrastructure.bring;

import no.clueless.opencargo.domain.model.FreightTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class BringPdfParserTest {
    BringPdfParser sut;

    @BeforeEach
    void setUp() {
        sut = new BringPdfParser();
    }

    @Test
    void parseMainZoneTable() {
        var file = new File("src/test/resources/Hovedsonetabell-pakker_01122025.pdf");
        var mainZoneTable = sut.parseMainZoneTable(file);
        System.out.println(mainZoneTable);
    }

    @Test
    void parseLocalZoneTable() {
        var file             = new File("src/test/resources/Lokalsone-Pakke-til-bedrift-Pakke-til-hentested_01122025.pdf");
        var localZoneTable   = sut.parseLocalZoneTable(file);
        var freightTerminals = FreightTerminal.of(localZoneTable);

        for (var freightTerminal : freightTerminals) {
            for (var entry : freightTerminal.getZones().entrySet()) {
                System.out.printf("%s -> %s (inbound: %d, outbound: %d)%n", freightTerminal.getPostalCode(), entry.getKey(), entry.getValue().getInboundZones(), entry.getValue().getOutboundZones());
            }
        }
    }
}