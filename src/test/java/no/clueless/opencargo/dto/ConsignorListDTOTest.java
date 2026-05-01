package no.clueless.opencargo.dto;

import jakarta.xml.bind.JAXBException;
import no.clueless.opencargo.util.XmlMarshaller;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsignorListDTOTest {
    @Test
    void unmarshalling_should_succeed() throws JAXBException {
        var consignor1 =  new ConsignorDTO();
        consignor1.setId(1);
        consignor1.setNumber("postennorgeas");
        consignor1.setName("Posten Norge AS");

        var consignor2 =  new ConsignorDTO();
        consignor2.setId(2);
        consignor2.setNumber("postnord");
        consignor2.setName("PostNord AS");

        var expected = new ConsignorListDTO();
        expected.setConsignors(List.of(consignor1, consignor2));

        var actual = XmlMarshaller.unmarshalResource("consignors.xml", ConsignorListDTO.class);
        assertEquals(expected, actual);
    }

}