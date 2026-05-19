package no.clueless.opencargo.infrastructure.persistence.bring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

class BringLocalZoneTableParserTest {
    BringLocalZoneTableParser sut;
    File                      file;

    @BeforeEach
    void setUp() {
        sut  = new BringLocalZoneTableParser();
        file = new File("src/test/resources/Lokalsone-Pakke-til-bedrift-Pakke-til-hentested_01122025.pdf");
    }

    @Test
    void parseLocalZoneTable() {
        var freightTerminals = sut.parseLocalZoneTable(file);

        for (var entry : freightTerminals.entrySet()) {
            var freightTerminal = entry.getValue();
            for (var zoneEntry : freightTerminal.getZones().entrySet()) {
                System.out.printf("%s -> %s (inbound: %d, outbound: %d)%n", freightTerminal.getPostalCode(), zoneEntry.getKey(), zoneEntry.getValue().getInboundZones(), zoneEntry.getValue().getOutboundZones());
            }
        }
    }
}