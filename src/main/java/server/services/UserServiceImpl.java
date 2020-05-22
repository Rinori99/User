package server.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.DTOs.UserRegisterTransport;
import server.DTOs.UserTransport;
import server.integration.models.SerializableEmail;
import server.integration.producers.EmailProducer;
import server.integration.producers.UserProducer;
import server.mappers.UserMapper;
import server.models.ParentStudentConnection;
import server.models.Role;
import server.models.User;
import server.repositories.ParentStudentConnectionRepo;
import server.repositories.UserRepo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private ParentStudentConnectionRepo parentStudentConnectionRepo;
    private EmailProducer emailProducer;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserProducer userProducer;

    public UserServiceImpl(UserRepo userRepo, ParentStudentConnectionRepo parentStudentConnectionRepo, EmailProducer emailProducer,
                           BCryptPasswordEncoder bCryptPasswordEncoder, UserProducer userProducer) {
        this.userRepo = userRepo;
        this.parentStudentConnectionRepo = parentStudentConnectionRepo;
        this.emailProducer = emailProducer;
        this.userProducer = userProducer;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserTransport getUserById(String id) {
        User user = userRepo.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        return new UserTransport(user.getId(), user.getFirstName(), user.getLastName(),
                user.getBirthDate(), user.getGender(), user.getRole().toString());
    }

    @Override
    public void register(UserRegisterTransport userRegister) {
        User savedUser = userRepo.save(new User(UUID.randomUUID().toString(), userRegister.getFirstName(), userRegister.getLastName(),
                userRegister.getEmail(), userRegister.getBirthDate(), userRegister.getGender(),
                Role.valueOf(userRegister.getRole()), bCryptPasswordEncoder.encode(userRegister.getPassword())));
        executePostUserCreationJobs(savedUser);
    }

    @Override
    public List<UserTransport> getChildrenByParent(String parentId) {
        List<ParentStudentConnection> psConnections = parentStudentConnectionRepo.findByParentId(parentId);
        List<User> children = psConnections.stream().map(ParentStudentConnection::getStudentId).collect(Collectors.toList());
        return children.stream().map(UserMapper::userToUserTransport).collect(Collectors.toList());
    }

    private void executePostUserCreationJobs(User user) {
        emailProducer.produce(new SerializableEmail(user.getEmail(), "Welcome to Education Management System",
                "Dear " + user.getFirstName() + ",\n\nYou have been successfully registered to the Education Management System.\nEnjoy!\n\n"+
                "Education Management System developers"));
        userProducer.sendNewUser(user);
    }

}
