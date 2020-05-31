package server.integration.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class SerializableParentStudentConnection implements Serializable {

    private final String id;
    private final String studentId;
    private final String parentId;

    public SerializableParentStudentConnection(@JsonProperty("id") String id,
                                               @JsonProperty("studentId") String studentId,
                                               @JsonProperty("parentId") String parentId) {
        this.id = id;
        this.studentId = studentId;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getParentId() {
        return parentId;
    }

    @Override
    public String toString() {
        return "ParentStudentConnectionConsumer{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }

}
