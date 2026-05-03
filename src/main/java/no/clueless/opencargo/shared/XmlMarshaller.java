package no.clueless.opencargo.shared;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

public class XmlMarshaller {
    private static final ConcurrentHashMap<Class<?>, JAXBContext> CONTEXT_CACHE = new ConcurrentHashMap<>();

    public static <T> T unmarshal(InputStream is, Class<T> clazz) throws JAXBException {
        ArgumentExceptionHelper.throwIfNull(is, "is");
        ArgumentExceptionHelper.throwIfNull(clazz, "clazz");

        try {
            var context = CONTEXT_CACHE.computeIfAbsent(clazz, c -> {
                try {
                    return JAXBContext.newInstance(c);
                } catch (JAXBException e) {
                    throw new RuntimeException("Failed to initialize JAXB context for " + clazz, e);
                }
            });

            var xmlInputFactory = XMLInputFactory.newFactory();
            xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
            xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);

            var xmlStreamReader = xmlInputFactory.createXMLStreamReader(is);

            var unmarshaller = context.createUnmarshaller();
            var result = unmarshaller.unmarshal(xmlStreamReader, clazz);

            return result.getValue();
        } catch (XMLStreamException e) {
            throw new JAXBException("Error creating XML stream reader", e);
        }
    }

    public static <T> T unmarshalResource(String filename, Class<T> clazz) throws JAXBException {
        ArgumentExceptionHelper.throwIfNullOrBlank(filename, "filename");
        ArgumentExceptionHelper.throwIfNull(clazz, "clazz");

        try(var is = XmlMarshaller.class.getClassLoader().getResourceAsStream(filename)) {
            if(is == null) {
                throw new JAXBException("Resource " + filename + " not found");
            }
            return unmarshal(is, clazz);
        } catch (IOException e) {
            throw new  JAXBException("Error reading resource " + filename, e);
        }
    }

    public static <T> T unmarshalResourceSilently(InputStream is, Class<T> clazz) {
        try {
            return unmarshal(is, clazz);
        } catch (JAXBException e) {
            throw new RuntimeException("Error reading resource " + clazz.getName(), e);
        }
    }

    public static <T> T unmarshalResourceSilently(String filename, Class<T> clazz) {
        try {
            return unmarshalResource(filename, clazz);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to unmarshal " + filename, e);
        }
    }
}
