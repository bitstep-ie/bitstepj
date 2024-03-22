package ie.bitstep.lang;

public interface UserProfile {
    public void setName(String name);

    public String getName();

    @Encrypted
    public void setCard(String card);

    @Encrypted
    public String getCard();
}
