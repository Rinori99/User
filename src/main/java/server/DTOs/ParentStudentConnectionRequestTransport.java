package server.DTOs;

import java.sql.Date;

public class ParentStudentConnectionRequestTransport extends BaseTransport {

    private String id;
    private String parentId;
    private String studentEmail;
    private Date dateCreated;
    private String schoolId;

    public ParentStudentConnectionRequestTransport() {

    }

//    public ParentStudentConnectionRequestTransport(String parentId, String studentEmail, Date dateCreated) {
//        this.parentId = parentId;
//        this.studentEmail = studentEmail;
//        this.dateCreated = dateCreated;
//    }
//
//    public ParentStudentConnectionRequestTransport(String id, String parentId, String studentEmail, Date dateCreated) {
//        this.id = id;
//        this.parentId = parentId;
//        this.studentEmail = studentEmail;
//        this.dateCreated = dateCreated;
//    }


    public ParentStudentConnectionRequestTransport(String parentId, String studentEmail, Date dateCreated, String schoolId) {
        this.parentId = parentId;
        this.studentEmail = studentEmail;
        this.dateCreated = dateCreated;
        this.schoolId = schoolId;
    }

    public ParentStudentConnectionRequestTransport(String id, String parentId, String studentEmail, Date dateCreated, String schoolId) {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
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
