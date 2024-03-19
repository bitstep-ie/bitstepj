package ie.bitstep.lang;

public class DogImpl implements Dog {
    public String color;

    public DogImpl() {

    }

    public DogImpl(String color) {
        this.color = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Object bark() {
        System.out.println("Bark " + color + " dog"); return this;
    }

    public Object fetch() {
        System.out.println("Fetch " + color + " dog");
        return this;
    }
}
