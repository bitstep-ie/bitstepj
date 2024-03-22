package ie.bitstep.lang;

public class UserProfileImpl implements UserProfile {
    private String name;
    private String card;

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
    public String getCard() {
        return card;
    }
}
