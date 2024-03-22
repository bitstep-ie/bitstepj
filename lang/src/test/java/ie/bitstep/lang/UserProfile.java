package ie.bitstep.lang;

public interface UserProfile {
    public void setName(String name);

    public String getName();

    @Encrypted
    @Lookup
    public void setCard(String card);

    @Encrypted
    public String getCard();

    public String getCardHash1();

    public String getCardHash2();
}
