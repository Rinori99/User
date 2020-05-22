package server.integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class SerializableUser implements Serializable {

    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;

    public SerializableUser(@JsonProperty("id") String id,
                            @JsonProperty("email") String email,
                            @JsonProperty("firstName") String firstName,
                            @JsonProperty("lastName") String lastName,
                            @JsonProperty("password") String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "SerializableUser{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
