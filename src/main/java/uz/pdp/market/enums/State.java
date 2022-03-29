package uz.pdp.market.enums;

import lombok.Getter;

@Getter
public enum State {
    UNAUTHORIZED("UNAUTHORIZED"),
    PHONE_NUMBER("PHONE_NUMBER"),
    FULL_NAME("FULL_NAME"),
    USER_NAME("USER_NAME"),
    PASSWORD("PASSWORD"),
    REGISTERED("REGISTERED"),
    MARKET("MARKET"),
    START("START"),
    DONE("DONE");

    private final String name;

    State(String name) {
        this.name = name;
    }

    public static State findByText(String text) {
        for (State value : State.values()) {
            if (value.getName().equalsIgnoreCase(text)) {
                return value;
            }
        }
        return null;
    }
}