package mipt.bit.models;

public class Letter {
    private String addressTo;
    private String addressFrom;

    public Letter(String addressTo, String addressFrom) {
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }
}
