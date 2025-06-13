package org.os.config;

import org.os.annotations.*;
import org.reflections.Reflections;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

public class ComponentScan {
    public static Set<Class<?>> scan(String basePackage){
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> components = new HashSet<>();
        components.addAll(reflections.getTypesAnnotatedWith(Controller.class));
        components.addAll(reflections.getTypesAnnotatedWith(Repository.class));
        components.addAll(reflections.getTypesAnnotatedWith(Service.class));
        components.addAll(reflections.getTypesAnnotatedWith(Secured.class));
        components.addAll(reflections.getTypesAnnotatedWith(Inject.class));
        components.addAll(reflections.getTypesAnnotatedWith(Entity.class));
        components.addAll(reflections.getTypesAnnotatedWith(Component.class));
        return components;
    }
}
