package server.mappers;

import server.DTOs.ParentStudentConnectionRequestTransport;
import server.models.ParentStudentConnectionRequest;

public class ParentStudentConnectionRequestMapper {

    public static ParentStudentConnectionRequestTransport pscRequestToPscRequestTransport(ParentStudentConnectionRequest pscRequest) {
        return new ParentStudentConnectionRequestTransport(pscRequest.getId(), pscRequest.getParentId().getId(), pscRequest.getStudentEmail(),
                pscRequest.getDateCreated(), pscRequest.getSchoolId());
    }

}
