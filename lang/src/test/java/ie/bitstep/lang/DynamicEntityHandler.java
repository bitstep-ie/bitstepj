package ie.bitstep.lang;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DynamicEntityHandler implements InvocationHandler {
    private final Base64.Decoder decoder = Base64.getDecoder();

    private final Base64.Encoder encoder = Base64.getEncoder();
    private final Object entity;

    public DynamicEntityHandler(Object entity) {
        this.entity = entity;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // find corresponding method in implementation class
        Method target = entity.getClass().getMethod(method.getName(), method.getParameterTypes());
        Encrypted encrypted = method.getDeclaredAnnotation(Encrypted.class);

        // check annotations, is this encrypted, is it lookup etc....
        if (encrypted != null) {
            if (args != null && args.length == 1) {
                args[0] = encoder.encodeToString(((String)args[0]).getBytes(StandardCharsets.UTF_8));
                return target.invoke(entity, args);
            }
            else {
                return new String(decoder.decode((String)target.invoke(entity, args)), StandardCharsets.UTF_8);
            }
        } else {
            // normal oul field, just call implementation method
            return target.invoke(entity, args);
        }
    }
}