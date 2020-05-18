package server.services;

import server.DTOs.ParentStudentConnectionRequestTransport;
import server.DTOs.ParentStudentConnectionTransport;

import java.util.List;

public interface ParentStudentConnectionService {

    void createParentStudentConnectionRequest(ParentStudentConnectionRequestTransport parentStudentConnection);

    ParentStudentConnectionTransport createParentStudentConnection(ParentStudentConnectionRequestTransport psConnectionRequestTransport);

    void deleteParentStudentConnectionRequest(String psConnectionRequestId);

    List<ParentStudentConnectionRequestTransport> getParentStudentConnectionRequestsBySchool(String schoolId);

}
