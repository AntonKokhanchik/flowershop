package flowershop.backend.enums;

public enum SessionAttribute {
    USER("sessionUser"),
    CART("sessionCart"),
    DETAILED_CART("sessionDetailedCart");

    private String value;

    SessionAttribute(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
