package server.DTOs;

import java.sql.Date;

public class ParentStudentConnectionTransport {

    private String id;
    private String parentId;
    private String studentId;
    private Date dateCreated;

    public ParentStudentConnectionTransport() {

    }

    public ParentStudentConnectionTransport(String parentId, String studentId, Date dateCreated) {
        this.parentId = parentId;
        this.studentId = studentId;
        this.dateCreated = dateCreated;
    }

    public ParentStudentConnectionTransport(String id, String parentId, String studentId, Date dateCreated) {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
