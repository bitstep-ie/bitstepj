package ie.bitstep.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicEntityHandler implements InvocationHandler {
    Object entity;

    public DynamicEntityHandler(Object entity) {
        this.entity = entity;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Invoked method: " + method.getName());

        // find corresponding method in implementation class
        Method target = entity.getClass().getMethod(method.getName(), method.getParameterTypes());
        Annotation[] annotation = method.getDeclaredAnnotations();

        // check annotations, is this encrypted, is it lookup etc....

        // normal oul field, just call implementation method
        return target.invoke(entity, args);
    }
}