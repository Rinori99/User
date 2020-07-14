package server.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "parent_student_connection")
public class ParentStudentConnection {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private User parentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private User studentId;

    @Column(name = "date_created")
    private Date dateCreated;

    public ParentStudentConnection() {

    }

    public ParentStudentConnection(String id, User parentId, User studentId, Date dateCreated) {
        this.id = id;
        this.parentId = parentId;
        this.studentId = studentId;
        this.dateCreated = dateCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getParentId() {
        return parentId;
    }

    public void setParentId(User parentId) {
        this.parentId = parentId;
    }

    public User getStudentId() {
        return studentId;
    }

    public void setStudentId(User studentId) {
        this.studentId = studentId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ParentStudentConnection) {
            return this.getId().equals(((ParentStudentConnection)obj).getId());
        }
        return false;
    }

}
