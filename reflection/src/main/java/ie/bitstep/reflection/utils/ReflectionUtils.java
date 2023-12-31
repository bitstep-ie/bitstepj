package ie.bitstep.reflection.utils;

import ie.bitstep.reflection.accessors.PropertyAccessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectionUtils {
    private static final Map<Class<?>, ClassInfo> classInfo = new HashMap<>();

    private ReflectionUtils() { // SONAR
        //
    }

    public static ClassInfo getClassInfo(Class<?> clazz) {
        return classInfo.computeIfAbsent(clazz, ClassInfo::new);
    }

    public static ClassInfo getClassInfo(Object o) {
        return classInfo.computeIfAbsent(o.getClass(), ClassInfo::new);
    }

    public static void setField(Object target, String name, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        PropertyAccessor<Object> pa = new PropertyAccessor<>(target.getClass(), name);
        pa.set(target, value);
    }

    public static Object getField(Object target, String name) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        PropertyAccessor<Object> pa = new PropertyAccessor<>(target.getClass(), name);
        return pa.get(target);
    }

    public Method getMethod(Class<?> clazz, String name) {
        return classInfo.computeIfAbsent(clazz, ClassInfo::new).getMethod(name);
    }

    public Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        return classInfo.computeIfAbsent(clazz, ClassInfo::new).getMethod(name, parameterTypes);
    }

    public List<MethodInfo> getMethodInfoByAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        return classInfo.computeIfAbsent(clazz, ClassInfo::new).getMethodInfoByAnnotation(annotation);
    }

    public List<MethodInfo> getMethodInfoByName(Class<?> clazz, String name) {
        return classInfo.computeIfAbsent(clazz, ClassInfo::new).getMethodInfoByName(name);
    }
}

