package ie.bitstep.lang;

public class UserProfileImpl implements UserProfile {
    private String name;

    private String card;

    private String cardHash1;

    private String cardHash2;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setCard(String card) {
        this.card = card;
    }

    @Override
    public void setCardHash1(String hash) {
        this.cardHash1 = hash;
    }

    @Override
    public void setCardHash2(String hash) {
        this.cardHash2 = hash;
    }

    @Override
    public String getCard() {
        return card;
    }

    @Override
    public String getCardHash1() {
        return cardHash1;
    }

    @Override
    public String getCardHash2() {
        return cardHash2;
    }
}
