package ie.bitstep.typesafety.utils;

public class TypeSafe<T> {
    private T thing;

    public TypeSafe(T thing) {
        this.thing = thing;
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if (that != null && this.getClass() == that.getClass()) {
            return this.hashCode() == that.hashCode();
        }

        return false;
    }

    public int hashCode() {
        return thing.hashCode();
    }

    public T get() {
        return thing;
    }

    void set(T thing) {
        this.thing = thing;
    }
}
