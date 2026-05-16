package no.clueless.opencargo.domain.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceLocator {
    private static final Logger                log      = LoggerFactory.getLogger(ServiceLocator.class);
    private final        Map<Class<?>, Object> registry = new ConcurrentHashMap<>();

    public <T> void register(Class<T> clazz, T instance) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        if (instance == null) {
            throw new IllegalArgumentException("instance cannot be null");
        }
        if (registry.containsKey(clazz)) {
            throw new IllegalArgumentException("instance already registered");
        }
        registry.put(clazz, instance);
    }

    public <T> Optional<T> lookup(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("clazz cannot be null");
        }
        if (!registry.containsKey(clazz)) {
            log.warn("{} is not registered", clazz.getName());
            return Optional.empty();
        }
        //noinspection unchecked
        return Optional.ofNullable((T) registry.get(clazz));
    }

    private static final class SingletonHolder {
        private static final ServiceLocator INSTANCE = new ServiceLocator();
    }

    public static ServiceLocator getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
