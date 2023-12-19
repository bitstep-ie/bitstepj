package ie.bitstep.reflection.utils;

import ie.bitstep.reflection.accessors.PropertyGetter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClassInfoTest {

    @PropertyGetter("test")
    public void testMethod() {

    }

    @PropertyGetter("test")
    public void testMethod(String s) {

    }

    @Test
    void getMethodByName() throws NoSuchMethodException {
        ClassInfo classInfo = new ClassInfo(this.getClass());
        Method method = classInfo.getMethod("testMethod");
        Method expected = this.getClass().getMethod("testMethod");

        assertThat(method).isEqualTo(expected);
    }

    @Test
    void getMethodByNameWithParams() throws NoSuchMethodException {
        ClassInfo classInfo = new ClassInfo(this.getClass());
        Method method = classInfo.getMethod("testMethod", String.class);

        assertThat(method).isEqualTo(this.getClass().getMethod("testMethod", String.class));
    }

    @Test
    void getMethodInfoByAnnotation() throws NoSuchMethodException {
        ClassInfo classInfo = new ClassInfo(this.getClass());
        List<MethodInfo> methods = classInfo.getMethodInfoByAnnotation(PropertyGetter.class);

        assertThat(methods).contains(new MethodInfo(this.getClass().getMethod("testMethod")),
                new MethodInfo(this.getClass().getMethod("testMethod", String.class)));
    }

    @Test
    void getMethodInfoByName() throws NoSuchMethodException {
        ClassInfo classInfo = new ClassInfo(this.getClass());
        List<MethodInfo> methods = classInfo.getMethodInfoByName("testMethod");

        assertThat(methods).contains(new MethodInfo(this.getClass().getMethod("testMethod")),
                new MethodInfo(this.getClass().getMethod("testMethod", String.class)));
    }
}