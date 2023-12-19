package ie.bitstep.logging.mapper;

import ie.bitstep.logging.annotations.Log;
import ie.bitstep.logging.processors.MaskProcessor;
import ie.bitstep.logging.annotations.Mask;
import ie.bitstep.logging.processors.AnnotationProcessor;
import ie.bitstep.reflection.accessors.PropertyAccessor;
import ie.bitstep.reflection.utils.ClassInfo;
import ie.bitstep.reflection.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LogMapper {
    private static final Map<Class<? extends Annotation>, AnnotationProcessor> staticAnnotationProcessors = new HashMap<>();

    static {
        staticAnnotationProcessors.put(Mask.class, new MaskProcessor());
    }

    private final Map<Class<? extends Annotation>, AnnotationProcessor> annotationProcessors = new HashMap<>();

    public LogMapper() {
        this.annotationProcessors.putAll(staticAnnotationProcessors);
    }

    public LogMapper(Map<Class<? extends Annotation>, AnnotationProcessor> annotationProcessors) {
        this.annotationProcessors.putAll(staticAnnotationProcessors);
        this.annotationProcessors.putAll(annotationProcessors);
    }

    public static void addStaticAnnotationProcessor(Class<? extends Annotation> annotation, AnnotationProcessor processor) {
        staticAnnotationProcessors.put(annotation, processor);
    }

    public void addAnnotationProcessor(Class<? extends Annotation> annotation, AnnotationProcessor processor) {
        annotationProcessors.put(annotation, processor);
    }

    public Map<String, Object> map(Object o) {
        String path = "$";
        Map<Object, String> visited = new HashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();

        return map(path, data, visited, o);
    }

    @SuppressWarnings("unchecked")
    Map<String, Object> map(String path, Map<String, Object> data, Map<Object, String> visited, Object o) {
        if (!visited.containsKey(o)) {
            ClassInfo ci = ReflectionUtils.getClassInfo(o);

            visited.put(o, path);

            for (PropertyAccessor<?> pa : ci.getAccessors()) {
                Optional<Log> log = (Optional<Log>) pa.getAnnotationByType(Log.class);

                if (log.isPresent()) {
                    if (pa.isCoreType()) {
                        data.put(pa.getFieldName(), shape(pa, o));
                    } else {
                        data.put(pa.getFieldName(), map(makePath(path, pa), new LinkedHashMap<>(), visited, pa.get(o)));
                    }
                }
            }
        } else {
            data.put("doc-ref", visited.get(o));
        }

        return data;
    }

    private static String makePath(String path, PropertyAccessor<?> pa) {
        return path + "." + pa.getFieldName();
    }

    @SuppressWarnings("unchecked")
    private Object shape(PropertyAccessor<?> pa, Object o) {
        Object value = pa.get(o);

        for (Annotation a : pa.getAnnotations().values()) {
            AnnotationProcessor ap;

            if ((ap = annotationProcessors.get(a.annotationType())) != null) {
                value = ap.apply(a, value);
            }
        }

        return value;
    }
}
