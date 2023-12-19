package ie.bitstep.reflection.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.List;

@Getter
@EqualsAndHashCode
public class MethodInfo {
    private final Method method;
    private final List<Class<?>> parameterTypes;

    public MethodInfo(Method method) {
        this.method = method;
        this.parameterTypes = List.of(method.getParameterTypes());
    }

    public boolean matches(Class<?>... parameterTypes) {
        if (this.parameterTypes.size() == parameterTypes.length) {
            int index = 0;

            for (Class<?> clazz : parameterTypes) {
                if (this.parameterTypes.get(index++) != clazz) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}

