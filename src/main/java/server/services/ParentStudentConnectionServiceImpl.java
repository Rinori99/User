package server.services;

import org.springframework.stereotype.Service;
import server.DTOs.ParentStudentConnectionRequestTransport;
import server.DTOs.ParentStudentConnectionTransport;
import server.PerRequestIdStorage;
import server.integration.models.SerializableParentStudentConnection;
import server.integration.producers.EmailProducer;
import server.integration.producers.StudentParentConnectionProducer;
import server.mappers.ParentStudentConnectionMapper;
import server.mappers.ParentStudentConnectionRequestMapper;
import server.models.ParentStudentConnection;
import server.models.ParentStudentConnectionRequest;
import server.models.User;
import server.repositories.ParentStudentConnectionRepo;
import server.repositories.ParentStudentConnectionRequestRepo;
import server.repositories.UserRepo;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParentStudentConnectionServiceImpl implements ParentStudentConnectionService {

    private ParentStudentConnectionRepo parentStudentConnectionRepo;
    private ParentStudentConnectionRequestRepo parentStudentConnectionRequestRepo;
    private UserRepo userRepo;
    private StudentParentConnectionProducer connectionProducer;
    private EmailProducer emailProducer;

    public ParentStudentConnectionServiceImpl(ParentStudentConnectionRepo parentStudentConnectionRepo,
                                              ParentStudentConnectionRequestRepo parentStudentConnectionRequestRepo,
                                              UserRepo userRepo,
                                              StudentParentConnectionProducer connectionProducer,
                                              EmailProducer emailProducer) {
        this.parentStudentConnectionRepo = parentStudentConnectionRepo;
        this.parentStudentConnectionRequestRepo = parentStudentConnectionRequestRepo;
        this.userRepo = userRepo;
        this.connectionProducer = connectionProducer;
        this.emailProducer = emailProducer;
    }

    @Override
    public void createParentStudentConnectionRequest(ParentStudentConnectionRequestTransport parentStudentConnectionRequest) {
        parentStudentConnectionRequest.setParentId(PerRequestIdStorage.getUserId());
        User parent = userRepo.findById(PerRequestIdStorage.getUserId()).orElseThrow(() ->
                new NoSuchElementException("Parent not found!"));
        ParentStudentConnectionRequest connectionRequest = new ParentStudentConnectionRequest(
                UUID.randomUUID().toString(), parent, parentStudentConnectionRequest.getStudentEmail(),
                parentStudentConnectionRequest.getDateCreated(), parentStudentConnectionRequest.getSchoolId());
        parentStudentConnectionRequestRepo.save(connectionRequest);
    }

    @Override
    public ParentStudentConnectionTransport createParentStudentConnection(String requestId) {
        ParentStudentConnectionRequest psConnectionRequest = parentStudentConnectionRequestRepo.findById(requestId).orElseThrow(() -> new RuntimeException("Connection request not found!"));
        User parent = userRepo.findById(psConnectionRequest.getParentId().getId()).orElseThrow(() -> new NoSuchElementException("Parent not found"));
        User student = userRepo.findByEmail(psConnectionRequest.getStudentEmail());
        if(student == null) {
            throw new NoSuchElementException("No student with the given email could be found!");
        }
        deleteParentStudentConnectionRequest(psConnectionRequest.getId());
        ParentStudentConnection psConnection = parentStudentConnectionRepo.save(
                new ParentStudentConnection(UUID.randomUUID().toString(), parent, student, new Date(System.currentTimeMillis())));
        executePostConnectionCreationJobs(psConnection);
        return ParentStudentConnectionMapper.psConnectionToPsConnectionTransport(psConnection);
    }

    private void executePostConnectionCreationJobs(ParentStudentConnection connection) {
        connectionProducer.sendParentStudentConnection(new SerializableParentStudentConnection(connection.getId(),
                connection.getStudentId().getId(), connection.getParentId().getId()));
        User parent = connection.getParentId();
        User student = connection.getStudentId();
        String subject = "Child connection verification confirmation";
        String content = "Dear " + parent.getFirstName() + " " + parent.getLastName() + ",\n\nYour request to connect to your child " +
                student.getFirstName() + " " + student.getLastName() + " has been approved. Now you can track " + student.getFirstName() +
                "'s performance at school through your account.\n\nEducation Management System developers";
        //emailProducer.produce(new SerializableEmail(parent.getEmail(), subject, content));
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
