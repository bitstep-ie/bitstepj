package ie.bitstep.reflection.accessors;

import ie.bitstep.reflection.annotations.Mask;
import ie.bitstep.reflection.annotations.MaskCard;
import ie.bitstep.reflection.annotations.Modifier;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PropertyAccessorTest {
    @MaskCard
    public String name = "Hello";
    private String privateName = "Goodbye";
    private String privateNameAnnotatedMethods = "Goodbye";

    private int i = 20;

    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    @PropertyGetter("privateNameAnnotatedMethods")
    public String getPrivateNameAnnotatedMethod() {
        return privateName;
    }

    @PropertySetter("privateNameAnnotatedMethods")
    public void setPrivateNameAnnotatedMethod(String privateName) {
        this.privateName = privateName;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }

    @Test
    void testConstructor() throws NoSuchFieldException {
        PropertyAccessor<String> pa = new PropertyAccessor<>(PropertyAccessorTest.class, "name");

        assertThat(pa.getClazz()).isEqualTo(PropertyAccessorTest.class);
        assertThat(pa.getFieldName()).isEqualTo("name");
        assertThat(pa.getField().getName()).isEqualTo("name");
    }

    @Test
    void testPublicField() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        PropertyAccessor<String> pa = new PropertyAccessor<>(PropertyAccessorTest.class, "name");

        assertTrue(pa.hasAnnotation(Modifier.class));
        assertTrue(pa.hasAnnotation(Mask.class));
        assertTrue(pa.hasAnnotation(MaskCard.class));

        assertThat(pa.get(this)).isEqualTo("Hello");

        pa.set(this, "Adios");

        assertThat(pa.get(this)).isEqualTo("Adios");
    }

    @Test
    void testPrivateFieldNoAnnoatatedGetSet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        PropertyAccessor<String> pa = new PropertyAccessor<>(PropertyAccessorTest.class, "privateName");

        assertThat(pa.getGetter()).isEqualTo(this.getClass().getMethod("getPrivateName"));
        assertThat(pa.getSetter()).isEqualTo(this.getClass().getMethod("setPrivateName", String.class));

        pa.set(this, "Adios");

        assertThat(pa.get(this)).isEqualTo("Adios");
    }

    @Test
    void testPrivateFieldAnnoatatedGetSet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        PropertyAccessor<String> pa = new PropertyAccessor<>(PropertyAccessorTest.class, "privateNameAnnotatedMethods");

        assertThat(pa.getGetter()).isEqualTo(this.getClass().getMethod("getPrivateNameAnnotatedMethod"));
        assertThat(pa.getSetter()).isEqualTo(this.getClass().getMethod("setPrivateNameAnnotatedMethod", String.class));

        pa.set(this, "Adios");
        assertThat(pa.get(this)).isEqualTo("Adios");
    }

    @Test
    void testIntFieldNoAnnoatatedGetSet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        PropertyAccessor<Integer> pa = new PropertyAccessor<>(PropertyAccessorTest.class, "i");

        pa.set(this, 100);
        assertThat(pa.get(this)).isEqualTo(100);
    }
}
