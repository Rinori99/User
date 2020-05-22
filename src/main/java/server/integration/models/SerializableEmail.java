package server.integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class SerializableEmail implements Serializable {

    private final String to;
    private final String subject;
    private final String text;

    public SerializableEmail(@JsonProperty("to") String to,
                             @JsonProperty("subject") String subject,
                             @JsonProperty("text") String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SerializableEmail{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
