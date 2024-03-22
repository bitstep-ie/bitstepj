package ie.bitstep.lang;

public class UserProfileImpl extends EncryptedBlob implements UserProfile {
    private String name;

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

    /**
     * Dummy implementations, will be ignored
     *
     */
    @Override
    public void setCard(String card) {
    }

    public void setCardHash1(String hash) {
        this.cardHash1 = hash;
    }

    public void setCardHash2(String hash) {
        this.cardHash2 = hash;
    }

    /**
     * Dummy implementations, will be ignored
     * @return
     */
    @Override
    public String getCard() {
        return null;
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
