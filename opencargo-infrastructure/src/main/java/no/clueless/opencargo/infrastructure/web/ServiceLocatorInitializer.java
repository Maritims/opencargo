package no.clueless.opencargo.infrastructure.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import no.clueless.opencargo.application.service.FreightService;
import no.clueless.opencargo.domain.shared.ServiceLocator;
import no.clueless.opencargo.infrastructure.persistence.xml.XmlFreightProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ServiceLocatorInitializer implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(ServiceLocatorInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        var locator = ServiceLocator.getInstance();

        try (var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("freight-products.xml")) {
            if (inputStream == null) {
                log.error("Failed to find freight-products.xml");
                throw new IllegalStateException("Failed to find freight-products.xml");
            }

            var freightProductRepository = new XmlFreightProductRepository(inputStream);
            var freightService           = new FreightService(freightProductRepository);
            var servletActionProcessor   = new ServletActionProcessor(
                    ServletActionRoute.GET("/find-products", new FindProductsAction(freightService)),
                    ServletActionRoute.GET("/find-eligible-products", new FindEligibleProductsAction(freightService))
            );
            locator.register(ServletActionProcessor.class, servletActionProcessor);
        } catch (IOException e) {
            log.error("Error while reading freight-products.xml", e);
            throw new RuntimeException(e);
        }
    }
}
