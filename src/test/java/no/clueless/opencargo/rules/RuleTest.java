package no.clueless.opencargo.rules;

import no.clueless.opencargo.dto.GeographyRuleDTO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    @Test
    void unmarshal() {
        try(var is = Rule.class.getClassLoader().getResourceAsStream("rules.xml")) {
            var rules = Rule.unmarshal(is);

            assertNotNull(rules);
            assertFalse(rules.getRules().isEmpty());

            var rule = rules.getRules().get(0);
            assertInstanceOf(GeographyRuleDTO.class, rule);
            assertNotNull(rule);
            assertEquals("Pakke til hentested og Pakkeboks er kun tilgjengelig i Norge", rule.getName());

            var geographyRule = (GeographyRuleDTO) rule;
            assertNotNull(geographyRule.getCountryConstraints());
            assertFalse(geographyRule.getCountryConstraints().isEmpty());

            var countryConstraint = geographyRule.getCountryConstraints().get(0);
            assertNotNull(countryConstraint);
            assertEquals("no", countryConstraint.getCountryCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}