package no.clueless.opencargo.infrastructure.persistence.bring;

import no.clueless.opencargo.domain.model.FreightProductId;
import no.clueless.opencargo.domain.physical.Weight;
import no.clueless.opencargo.domain.physical.WeightUnit;
import no.clueless.opencargo.domain.shared.Money;
import no.clueless.opencargo.domain.shared.PostalCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BringFreightNetworkRepositoryTest {
    BringMainZoneTableParser      mainZoneTableParser;
    BringLocalZoneTableParser     localZoneTableParser;
    BringZonePriceTableParser     zonePriceTableParser;
    BringFreightNetworkRepository sut;
    File                          mainZoneTableFile;
    File                          localZoneTableFile;
    File                          zonePriceTableFile;

    public static Stream<Arguments> getZonePrice() {
        return Stream.of(
                Arguments.of(1, 1, 215.0),
                Arguments.of(2, 1, 225.0),
                Arguments.of(3, 1, 230.0),
                Arguments.of(5, 1, 248.0),
                Arguments.of(7, 1, 270.0),
                Arguments.of(10, 1, 308.0),
                Arguments.of(15, 1, 350.0),
                Arguments.of(20, 1, 392.0)
        );
    }

    @BeforeEach
    void setUp() {
        mainZoneTableParser  = new BringMainZoneTableParser(new PdfVerticalOffsetFinder());
        localZoneTableParser = new BringLocalZoneTableParser();
        zonePriceTableParser = new BringZonePriceTableParser(new PdfVerticalOffsetFinder());
        mainZoneTableFile    = new File("src/test/resources/Hovedsonetabell-pakker_01122025.pdf");
        localZoneTableFile   = new File("src/test/resources/Lokalsone-Pakke-til-bedrift-Pakke-til-hentested_01122025.pdf");
        zonePriceTableFile   = new File("src/test/resources/Prisliste-Pakke-til-bedrift_01122025.pdf");
        sut                  = new BringFreightNetworkRepository(mainZoneTableParser, localZoneTableParser, zonePriceTableParser, Set.of(
                new BringZoneFileSet(mainZoneTableFile, localZoneTableFile, Map.of(new FreightProductId("5800"), zonePriceTableFile), Set.of(new FreightProductId("5800")))
        ));
    }

    @Test
    void findByFreightProductId() {
        var freightProductId = new FreightProductId("5800");
        var freightNetwork   = sut.findByFreightProductId(freightProductId).orElseThrow();
        var actual           = freightNetwork.getTotalZonesBetween(new PostalCode("3110"), new PostalCode("6409"));
        assertEquals(5, actual);
    }

    @ParameterizedTest
    @MethodSource
    void getZonePrice(int weight, int zone, double expected) {
        var freightProductId = new FreightProductId("5800");
        var freightNetwork   = sut.findByFreightProductId(freightProductId).orElseThrow();
        var actual           = freightNetwork.getZonePrice(freightProductId, new Weight(BigDecimal.valueOf(weight), WeightUnit.KILOGRAM), zone).orElseThrow();
        assertEquals(new Money(new BigDecimal(expected), Currency.getInstance("NOK")), actual);
    }
}