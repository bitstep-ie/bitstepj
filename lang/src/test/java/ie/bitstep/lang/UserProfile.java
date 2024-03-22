package ie.bitstep.lang;

public interface UserProfile {
    public void setName(String name);

    public String getName();

    @Encrypted
    @Lookup
    public void setCard(String card);

    public void setCardHash1(String hash);

    public void setCardHash2(String hash);

    @Encrypted
    public String getCard();

    public String getCardHash1();

    public String getCardHash2();
}
