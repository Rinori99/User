package server.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "parent_student_connection_request")
public class ParentStudentConnectionRequest {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private User parentId;

    @Column(name = "student_email")
    private String studentEmail;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "school_id")
    private String schoolId;

    public ParentStudentConnectionRequest() {

    }

//    public ParentStudentConnectionRequest(String id, User parentId, String studentEmail, Date dateCreated) {
//        this.id = id;
//        this.parentId = parentId;
//        this.studentEmail = studentEmail;
//        this.dateCreated = dateCreated;
//    }


    public ParentStudentConnectionRequest(String id, User parentId, String studentEmail, Date dateCreated, String schoolId) {
        this.id = id;
        this.parentId = parentId;
        this.studentEmail = studentEmail;
        this.dateCreated = dateCreated;
        this.schoolId = schoolId;
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

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

}
