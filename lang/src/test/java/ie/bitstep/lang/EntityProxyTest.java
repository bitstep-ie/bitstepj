package ie.bitstep.lang;

//import javassist.util.proxy.MethodFilter;
//import javassist.util.proxy.MethodHandler;
//import javassist.util.proxy.Proxy;
//import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class EntityProxyTest {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Dog dog = (Dog) Proxy.newProxyInstance(
                EntityProxyTest.class.getClassLoader(),
                new Class[]{Dog.class},
                new DynamicEntityHandler(new DogImpl("red")));

        dog.bark();
    }

        //    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        Dog dog = (Dog) viaProxy(Dog.class);
//        dog.bark();
//        dog.fetch();
//    }
//
//    private static Object viaProxy(Class<?> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
//        ProxyFactory f = new ProxyFactory();
//        f.setSuperclass(clazz);
//        f.setFilter(new MethodFilter() {
//            public boolean isHandled(Method m) {
//                // ignore finalize()
//                return !m.getName().equals("finalize");
//            }
//        });
//
//        MethodHandler mi = new MethodHandler() {
//            public Object invoke(Object object, Method method, Method proxy,
//                                 Object[] args) throws Throwable {
//                return proxy.invoke(object, args);  // execute the original method.
//            }
//        };
//
//        Class<?> proxyCreator = f.createClass();
//        Constructor<?> constructor = proxyCreator.getConstructor(String.class);
//        Proxy proxy = (Proxy) constructor.newInstance("red");
//        proxy.setHandler(mi);
//
//        return proxy;
//    }
    }