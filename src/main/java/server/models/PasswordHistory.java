package server.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "password_history")
public class PasswordHistory {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "password")
    private String password;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    public PasswordHistory() {

    }

//    public PasswordHistory(User userId, String password, Timestamp dateCreated) {
//        this.userId = userId;
//        this.password = password;
//        this.dateCreated = dateCreated;
//    }

    public PasswordHistory(String id, User userId, String password, Timestamp dateCreated) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.dateCreated = dateCreated;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

}
