package ie.bitstep.lang;

public class Dog {
    public String color;

    public Dog() {

    }

    public Dog(String color) {
        this.color = color;
    }

    public void bark() {
        System.out.println("Bark");
    }

    public void fetch() {
        System.out.println("Fetch");
    }
}
