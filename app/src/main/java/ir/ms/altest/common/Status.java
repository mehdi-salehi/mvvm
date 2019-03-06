package ir.ms.altest.common;

public enum Status {
    PENDING(0),
    INPROGRESS(1),
    DELIVERY(2),
    DONE(3);

    private final int value;

    public int getValue() {
        return this.value;
    }

    private Status(int value) {
        this.value = value;
    }
}
