package ie.bitstep.lang;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class DynamicEntityHandler implements InvocationHandler {
    private final Map<Object, Map<String, String>> entityEncryptedAttributes = new HashMap<>();

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
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // check annotations, is this encrypted, is it lookup etc....
        if (encrypted != null) {
            if (args != null && args.length == 1) { // assumed setter
                return doSetter(method, args, digest, target);
            } else { // assumed getter
                return doGetter(args, target);
            }
        } else {
            // normal oul field, just call implementation method
            return target.invoke(entity, args);
        }
    }

    private String doGetter(Object[] args, Method target) throws IllegalAccessException, InvocationTargetException {
        if (entity instanceof EncryptedBlob) {
            Map<String, String> encryptedAttributes = entityEncryptedAttributes.get(entity);

            return encryptedAttributes.get(target.getName().replace("get", ""));
        }
        else {
            return new String(decoder.decode((String) target.invoke(entity, args)), StandardCharsets.UTF_8);
        }
    }

    private Object doSetter(Method method, Object[] args, MessageDigest digest, Method target) throws IllegalAccessException, InvocationTargetException {
        Lookup lookup = method.getDeclaredAnnotation(Lookup.class);
        String s = (String) args[0];

        if (lookup != null) {
            setHash(method, "Hash1", digest, s);
            setHash(method, "Hash2", digest, s);
        }

        if (entity instanceof EncryptedBlob) {
            Map<String, String> encryptedAttributes = entityEncryptedAttributes.computeIfAbsent(entity, e -> new HashMap<>());

            encryptedAttributes.put(target.getName().replace("set", ""), (String) args[0]);
        } else {
            args[0] = encoder.encodeToString(s.getBytes(StandardCharsets.UTF_8));

            return target.invoke(entity, args);
        }

        return null;
    }

    private void setHash(Method method, String whichHash, MessageDigest digest, String s) throws IllegalAccessException, InvocationTargetException {
        try {
            Method setHash1 = entity.getClass().getMethod(method.getName() + whichHash, method.getParameterTypes());

            setHash1.invoke(entity, encoder.encodeToString(digest.digest(
                    s.getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchMethodException e) {
        }
    }
}