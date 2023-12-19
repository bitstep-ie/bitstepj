package ie.bitstep.logging.processors;

import java.lang.annotation.Annotation;

public interface AnnotationProcessor {
    Object apply(Annotation annotation, Object value);
}
