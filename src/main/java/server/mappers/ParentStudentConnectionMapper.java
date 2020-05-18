package server.mappers;

import server.DTOs.ParentStudentConnectionTransport;
import server.models.ParentStudentConnection;

public class ParentStudentConnectionMapper {

    public static ParentStudentConnectionTransport psConnectionToPsConnectionTransport(ParentStudentConnection psConnection) {
        return new ParentStudentConnectionTransport(psConnection.getId(), psConnection.getParentId().getId(),
                psConnection.getStudentId().getId(), psConnection.getDateCreated());
    }

}
