package ie.bitstep.reflection.accessors;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import ie.bitstep.reflection.utils.ClassInfo;
import ie.bitstep.reflection.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class PropertyAccessor<T> {
    private Class<?> clazz;
    private String fieldName;
    private Field field;
    private Method getter;
    private Method setter;
    private final Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<>();

    public PropertyAccessor(Class<?> clazz, String fieldName) {
        try {
            init(null, clazz, clazz.getDeclaredField(fieldName));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public PropertyAccessor(Class<?> clazz, Field field) {
        init(null, clazz, field);
    }

    public PropertyAccessor(ClassInfo classInfo, Field field) {
        init(classInfo, classInfo.getClazz(), field);
    }

    private void init(ClassInfo classInfo, Class<?> clazz, Field field) {
        this.getter = null;
        this.setter = null;
        this.clazz = clazz;
        this.field = field;
        this.fieldName = field.getName();

        populateAccessors(clazz, field);
        populateAnnotations(field);
        initialiseGetterSetter(classInfo, clazz, field);
    }

    private void initialiseGetterSetter(ClassInfo classInfo, Class<?> clazz, Field field) {
        ClassInfo ci = getClassInfo(classInfo, clazz);
        getter = getter != null ? getter : ci.getMethod("get" + StringUtils.capitalize(fieldName));
        setter = setter != null ? setter : ci.getMethod("set" + StringUtils.capitalize(fieldName), field.getType());
    }

    private void populateAccessors(Class<?> clazz, Field field) {
        Accessor accessor = field.getAnnotation(Accessor.class);

        if (accessor != null) {
            if (!accessor.getter().isEmpty()) {
                getter = ReflectionUtils.getClassInfo(clazz).getMethod(accessor.getter());
            }

            if (!accessor.setter().isEmpty()) {
                setter = ReflectionUtils.getClassInfo(clazz).getMethod(accessor.setter(), field.getType());
            }
        }
    }

    private void populateAnnotations(Class<? extends Annotation> type) {
        for (Annotation a : type.getAnnotations()) {
            if (!a.annotationType().getPackageName().startsWith("java.lang.") && !annotations.containsKey(a.annotationType())) {
                annotations.put(a.annotationType(), a);
            }
        }
    }

    private void populateAnnotations(Field type) {
        for (Annotation a : type.getAnnotations()) {
            annotations.put(a.annotationType(), a);
            populateAnnotations(a.annotationType());
        }
    }

    private static ClassInfo getClassInfo(ClassInfo classInfo, Class<?> clazz) {
        return classInfo != null ? classInfo : ReflectionUtils.getClassInfo(clazz);
    }

    @SuppressWarnings("unchecked")
    public Optional<T> getAnnotationByType(Class<? extends Annotation> annotation) {
        return Optional.ofNullable((T)annotations.get(annotation));
    }

    public boolean isCoreType() {
        Class<?> type = field.getType();

        return type.isAssignableFrom(String.class)
                || type.isAssignableFrom(Boolean.class)
                || type.isAssignableFrom(Long.class)
                || type.isAssignableFrom(Integer.class)
                || type.isAssignableFrom(Float.class)
                || type.isAssignableFrom(Double.class)
                ;
    }

    @SuppressWarnings("unchecked")
    public T get(Object o) {
        try {
            if (getter != null) {
                return (T) getter.invoke(o);
            }

            return (T) field.get(o);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // use an accessor method, of throw exception
            throw new RuntimeException(e);
        }
    }

    public void set(Object o, T value) {
        try {
            if (setter != null) {
                setter.invoke(o, value);
            } else {
                field.set(o, value); // NOSONAR
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            // use an accessor method, of throw exception
            throw new RuntimeException(e);
        }
    }
}

