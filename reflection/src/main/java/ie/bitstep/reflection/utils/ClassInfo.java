package ie.bitstep.reflection.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ie.bitstep.reflection.accessors.PropertyAccessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class ClassInfo {
    private final Class<?> clazz;
    private final Map<String, List<MethodInfo>> methods = new LinkedHashMap<>();
    private final Map<Class<? extends Annotation>, List<MethodInfo>> methodsByAnnotation = new LinkedHashMap<>();
    private final Map<String, PropertyAccessor> accessors = new LinkedHashMap<>();

    public ClassInfo(Class<?> clazz) {
        this.clazz = clazz;
        populateMethodCache();
        populateAccessorCache();
    }

    private void populateMethodCache() {
        for (Method m : clazz.getDeclaredMethods()) {
            methods.computeIfAbsent(m.getName(), name -> new ArrayList<>()).add(new MethodInfo(m));
            for (Annotation annotation : m.getAnnotations()) {
                methodsByAnnotation.computeIfAbsent(annotation.annotationType(), method -> new ArrayList<>()).add(new MethodInfo(m));
            }
        }
    }

    private void populateAccessorCache() {
        for (Field f : clazz.getDeclaredFields()) {
            accessors.computeIfAbsent(f.getName(), name -> new PropertyAccessor(this, f));
        }
    }

    public Collection<PropertyAccessor> getAccessors() {
        return accessors.values();
    }

    public Method getMethod(String name) {
        List<MethodInfo> methodInfo = methods.get(name);

        if (methodInfo != null) {
            for (MethodInfo mi : methodInfo) {
                if (mi.getParameterTypes().isEmpty()) {
                    return mi.getMethod();
                }
            }
        }

        return null;
    }

    public Method getMethod(String name, Class<?>... parameterTypes) {
        List<MethodInfo> methodInfo = methods.get(name);

        if (methodInfo != null) {
            for (MethodInfo mi : methodInfo) {
                if (mi.matches(parameterTypes)) {
                    return mi.getMethod();
                }
            }
        }

        return null;
    }

    public List<MethodInfo> getMethodInfoByAnnotation(Class<? extends Annotation> annotation) {
        return methodsByAnnotation.get(annotation);
    }

    public List<MethodInfo> getMethodInfoByName(String name) {
        return methods.get(name);
    }
}
