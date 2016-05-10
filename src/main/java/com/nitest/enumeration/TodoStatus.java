package com.nitest.enumeration;

import java.util.Objects;

public enum  TodoStatus {
    PENDING(0, "PENDING"),
    DONE(1, "DONE");

    private final Integer value;
    private final String textValue;

    TodoStatus(Integer value, String textValue){
        this.value = value;
        this.textValue = textValue;
    }
    public Integer toNumberValue() {
        return this.value;
    }

    /**
     * @return Returns the text value of the enum value
     */
    public String toTextValue() {
        return this.textValue;
    }

    /**
     * @param textValue The integer value to look up
     * @return Returns the enum value matching the integer value
     */
    public static TodoStatus fromTextValue(String textValue) {
        for (TodoStatus status : TodoStatus.values()) {
            if (Objects.equals(status.toTextValue(), textValue)) {
                return status;
            }
        }
        return null;
    }

    public static TodoStatus fromNumberValue(int value) {
        for (TodoStatus status : TodoStatus.values()) {
            if (status.toNumberValue() == value) {
                return status;
            }
        }
        return null;
    }

}
