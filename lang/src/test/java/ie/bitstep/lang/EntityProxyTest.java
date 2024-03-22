package ie.bitstep.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.security.NoSuchAlgorithmException;

public class EntityProxyTest {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchAlgorithmException {
        // this horrible block needs to be genericised
        UserProfile userProfile = (UserProfile) Proxy.newProxyInstance(
                EntityProxyTest.class.getClassLoader(),
                new Class[]{UserProfile.class},
                new DynamicEntityHandler(new UserProfileImpl()));

        userProfile.setName("John Allen");
        userProfile.setCard("5222098058761278");

        System.out.printf("%s: %s (h1: %s, h2: %s)\n",
                userProfile.getName(),
                userProfile.getCard(),
                userProfile.getCardHash1(),
                userProfile.getCardHash2());
    }
}