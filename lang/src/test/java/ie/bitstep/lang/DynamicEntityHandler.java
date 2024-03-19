package ie.bitstep.lang;

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

        return entity;
    }
}