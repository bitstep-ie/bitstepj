package ie.bitstep.lang;

public class EncryptedBlob {
//    @Column("encrypted")
    private String blob;

    public void setBlob(String value) {
        blob = value;
    }

    public String get() {
        return blob;
    }
}
