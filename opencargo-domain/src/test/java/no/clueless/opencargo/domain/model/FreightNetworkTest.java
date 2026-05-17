package no.clueless.opencargo.domain.model;

import no.clueless.opencargo.domain.shared.PostalCode;
import no.clueless.opencargo.domain.shared.graph.UndirectedEdge;
import no.clueless.opencargo.domain.shared.graph.UndirectedMatrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FreightNetworkTest {
    Map<PostalCode, FreightTerminal> freightTerminals;

    @BeforeEach
    void setUp() {
        freightTerminals = Map.of(
                new PostalCode("3200"), new FreightTerminal(new PostalCode("3200"), Set.of(new LocalZone(new PostalCode("3110"), 0, 0))),
                new PostalCode("6401"), new FreightTerminal(new PostalCode("6401"), Set.of(new LocalZone(new PostalCode("6409"), 0, 1)))
        );
    }

    @Test
    void getTotalZonesBetween() {
        // arrange
        var undirectedMatrix = UndirectedMatrix.of(Set.of(new UndirectedEdge<>(new PostalCode("3200"), new PostalCode("6401"), 4)));
        var freightNetwork   = new FreightNetwork(undirectedMatrix, freightTerminals);
        var expected         = 5;
        var from             = new PostalCode("3110");
        var to               = new PostalCode("6409");

        // act
        var actual = freightNetwork.getTotalZonesBetween(from, to);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void create_from_csv() {
        // arrange
        var lines = new ArrayList<>(List.of("PostalCode,1000,1700,2200,2300,2670,3000,3200,3550,4033,4600,5000,5500,6000,6401,6700,6800,6880,7000,7374,8000,8371,8403,8510,8600,8800,8910,9000,9400,9510,9846",
                "1000,0,2,2,2,3,2,2,3,3,3,3,3,3,3,4,4,4,3,3,4,5,5,5,5,5,5,4,5,5,5",
                "1700,2,0,2,3,3,2,2,3,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5",
                "2200,2,2,0,2,3,2,2,3,4,3,3,4,4,4,4,4,4,3,3,5,5,5,5,5,5,5,5,5,5,5",
                "2300,2,3,2,0,1,2,3,3,4,4,4,4,4,4,4,4,4,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "2670,3,3,3,1,0,3,3,3,4,4,4,4,4,4,4,4,4,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "3000,2,2,2,2,3,0,2,2,3,3,3,3,4,4,4,4,4,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "3200,2,2,2,3,3,2,0,2,3,3,4,3,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5",
                "3550,3,3,3,3,3,2,2,0,4,3,3,3,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5",
                "4033,3,4,4,4,4,3,3,4,0,3,3,2,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5",
                "4600,3,4,3,4,4,3,3,3,3,0,3,3,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5",
                "5000,3,4,3,4,4,3,4,3,3,3,0,2,3,3,3,3,3,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "5500,3,4,4,4,4,3,3,3,2,3,2,0,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5",
                "6000,3,4,4,4,4,4,4,4,4,4,3,4,0,2,3,3,3,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "6401,3,4,4,4,4,4,4,4,4,4,3,4,2,0,3,3,3,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "6700,4,4,4,4,4,4,4,4,4,4,3,4,3,3,0,3,3,4,5,5,5,5,5,5,5,5,5,5,5,5",
                "6800,4,4,4,4,4,4,4,4,4,4,3,4,3,3,3,0,2,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "6880,4,4,4,4,4,4,4,4,4,4,3,4,3,3,3,2,0,3,4,5,5,5,5,5,5,5,5,5,5,5",
                "7000,3,4,3,3,3,3,4,4,4,4,3,4,3,3,4,3,3,0,2,3,4,5,5,4,4,4,5,5,5,5",
                "7374,3,4,3,4,4,4,4,4,4,4,4,4,4,4,5,4,4,2,0,3,4,4,5,5,4,4,4,5,5,5",
                "8000,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,3,3,0,2,3,2,2,3,4,3,3,4,4",
                "8371,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,4,2,0,3,3,4,5,5,4,4,5,5",
                "8403,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,3,3,0,3,4,5,4,4,3,4,5",
                "8510,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2,3,3,0,4,5,5,3,3,4,5",
                "8600,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,5,2,4,4,4,0,2,3,4,4,5,5",
                "8800,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,4,3,5,5,5,2,0,3,4,4,5,5",
                "8910,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,4,4,5,4,5,4,4,0,4,4,5,5",
                "9000,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,3,4,4,3,5,5,4,0,3,4,4",
                "9400,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,2,3,3,3,4,4,4,3,0,4,5",
                "9510,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,5,4,4,5,5,5,4,4,0,3",
                "9846,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,5,5,5,5,5,5,4,5,3,0"));
        var undirectedMatrix = UndirectedMatrix.fromLines(lines, PostalCode::new);
        var freightNetwork   = new FreightNetwork(undirectedMatrix, freightTerminals);
        var expected         = 5;

        // act
        var actual = freightNetwork.getTotalZonesBetween(new PostalCode("3110"), new PostalCode("6409"));

        // asserẗ́
        assertEquals(expected, actual);
    }
}