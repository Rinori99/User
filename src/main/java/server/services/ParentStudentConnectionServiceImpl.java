package server.services;

import org.springframework.stereotype.Service;
import server.DTOs.ParentStudentConnectionRequestTransport;
import server.DTOs.ParentStudentConnectionTransport;
import server.mappers.ParentStudentConnectionMapper;
import server.mappers.ParentStudentConnectionRequestMapper;
import server.models.ParentStudentConnection;
import server.models.ParentStudentConnectionRequest;
import server.models.User;
import server.repositories.ParentStudentConnectionRepo;
import server.repositories.ParentStudentConnectionRequestRepo;
import server.repositories.UserRepo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParentStudentConnectionServiceImpl implements ParentStudentConnectionService {

    private ParentStudentConnectionRepo parentStudentConnectionRepo;
    private ParentStudentConnectionRequestRepo parentStudentConnectionRequestRepo;
    private UserRepo userRepo;

    public ParentStudentConnectionServiceImpl(ParentStudentConnectionRepo parentStudentConnectionRepo,
                                              ParentStudentConnectionRequestRepo parentStudentConnectionRequestRepo,
                                              UserRepo userRepo) {
        this.parentStudentConnectionRepo = parentStudentConnectionRepo;
        this.parentStudentConnectionRequestRepo = parentStudentConnectionRequestRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void createParentStudentConnectionRequest(ParentStudentConnectionRequestTransport parentStudentConnection) {
        User parent = userRepo.findById(parentStudentConnection.getParentId()).orElseThrow(() ->
                new NoSuchElementException("Parent not found!"));
        ParentStudentConnectionRequest connectionRequest = new ParentStudentConnectionRequest(
                UUID.randomUUID().toString(), parent, parentStudentConnection.getStudentEmail(),
                parentStudentConnection.getDateCreated(), parentStudentConnection.getSchoolId());
        parentStudentConnectionRequestRepo.save(connectionRequest);
    }

    @Override
    public ParentStudentConnectionTransport createParentStudentConnection(ParentStudentConnectionRequestTransport psConnectionRequestTransport) {
        User parent = userRepo.findById(psConnectionRequestTransport.getParentId()).orElseThrow(() -> new NoSuchElementException("Parent not found"));
        User student = userRepo.findByEmail(psConnectionRequestTransport.getStudentEmail());
        if(student == null) {
            throw new NoSuchElementException("No student with the given email could be found!");
        }
        deleteParentStudentConnectionRequest(psConnectionRequestTransport.getId());
        ParentStudentConnection psConnection = parentStudentConnectionRepo.save(
                new ParentStudentConnection(UUID.randomUUID().toString(), parent, student, psConnectionRequestTransport.getDateCreated()));
        return ParentStudentConnectionMapper.psConnectionToPsConnectionTransport(psConnection);
    }

    @Override
    public void deleteParentStudentConnectionRequest(String psConnectionRequestId) {
        parentStudentConnectionRequestRepo.deleteById(psConnectionRequestId);
    }

    @Override
    public List<ParentStudentConnectionRequestTransport> getParentStudentConnectionRequestsBySchool(String schoolId) {
        List<ParentStudentConnectionRequest> parentStudentConnectionRequests = parentStudentConnectionRequestRepo.findBySchoolId(schoolId);
        return parentStudentConnectionRequests.stream().map(ParentStudentConnectionRequestMapper::pscRequestToPscRequestTransport)
                .collect(Collectors.toList());
    }

}
