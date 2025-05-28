package org.os.config;

import java.util.HashMap;
import java.util.Map;

public class IocContainer {
    private Map<Class<?>, Object> beans = new HashMap<>();

    public void registerBean(Class<?> clazz, Object instance) {
        beans.put(clazz, instance);
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}

