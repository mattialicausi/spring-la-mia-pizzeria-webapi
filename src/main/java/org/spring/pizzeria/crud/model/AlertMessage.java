package org.spring.pizzeria.crud.model;

public class AlertMessage {

    public enum AlertMessageType {
        SUCCESS, TYPE
    }

    private String text;
    private AlertMessageType type;

    public AlertMessage(AlertMessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public AlertMessage() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AlertMessageType getType() {
        return type;
    }

    public void setType(AlertMessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AlertMessage{" +
                "text='" + text + '\'' +
                ", type=" + type +
                '}';
    }
}
