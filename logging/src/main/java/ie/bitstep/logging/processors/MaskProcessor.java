package ie.bitstep.logging.processors;

import ie.bitstep.logging.annotations.Mask;

import java.lang.annotation.Annotation;

public class MaskProcessor implements AnnotationProcessor {
    @Override
    public Object apply(Annotation annotation, Object value) {
        return mask((Mask) annotation, value);
    }

    private Object mask(Mask mask, Object value) {
        String s = value.toString();

        return s.substring(0, mask.preclear()) +
                makeMask(s, mask) +
                s.substring(s.length() - mask.postclear());
    }

    private String makeMask(String s, Mask mask) {
        StringBuilder masked = new StringBuilder();
        int len = s.length() - (mask.preclear() + mask.postclear());

        for (int i = 0; i < len; i++) {
            masked.append(mask.with());
        }

        return masked.toString();
    }
}
