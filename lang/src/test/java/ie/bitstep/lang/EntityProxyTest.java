package ie.bitstep.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class EntityProxyTest {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Dog dog = (Dog) Proxy.newProxyInstance(
                EntityProxyTest.class.getClassLoader(),
                new Class[]{Dog.class},
                new DynamicEntityHandler(new DogImpl("red")));

        dog.setColor("black");
        dog.bark();
    }
}