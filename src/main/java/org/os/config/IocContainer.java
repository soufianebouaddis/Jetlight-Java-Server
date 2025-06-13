package org.os.config;

import org.os.annotations.Inject;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class IocContainer {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public void register(Class<?> clazz) {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            System.err.println(instance.getClass().getName());
            //register the class itself
            beans.put(clazz, instance);
            //register class interfaces are implemented
            for (Class<?> interfaces : clazz.getInterfaces()){
                beans.put(interfaces,instance);
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot instantiate: " + clazz.getName(), e);
        }
    }

    public void injectDependencies() {
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Object dependency = getBean(field.getType());
                    if (dependency != null) {
                        field.setAccessible(true);
                        try {
                            field.set(bean, dependency);
                        } catch (Exception e) {
                            throw new RuntimeException("DI failed", e);
                        }
                    }
                }
            }
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }

    public Collection<Object> getAllBeans() {
        return beans.values();
    }
}


