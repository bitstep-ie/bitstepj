package ie.bitstep.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class EntityProxyTest {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        UserProfile userProfile = (UserProfile) Proxy.newProxyInstance(
                EntityProxyTest.class.getClassLoader(),
                new Class[]{UserProfile.class},
                new DynamicEntityHandler(new UserProfileImpl()));

        userProfile.setName("John Allen");
        userProfile.setCard("5222098058761278");

        System.out.printf("%s: %s\n", userProfile.getName(), userProfile.getCard());
    }
}