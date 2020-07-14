package services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.DTOs.UserRegisterTransport;
import server.DTOs.UserTransport;
import server.PerRequestIdStorage;
import server.mappers.UserMapper;
import server.models.ParentStudentConnection;
import server.models.Role;
import server.models.User;
import server.repositories.ParentStudentConnectionRepo;
import server.repositories.UserRepo;
import server.services.UserServiceImpl;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private ParentStudentConnectionRepo parentStudentConnectionRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User mockedUser;
    private User mockedParentUser;
    private long studentBirthDate = System.currentTimeMillis() - 20 * 365 * 24 * 60 * 60 * 1000L;
    private long parentBirthDate = System.currentTimeMillis() - 40 * 365 * 24 * 60 * 60 * 1000L;
    private long connectionCreationDate = System.currentTimeMillis();

    @Before
    public void setup() {
        PerRequestIdStorage.setUserId("5211e915-c3e2-4dcb-0776-c7b900f38ab7");
        mockedUser = new User("5211e915-c3e2-4dcb-0776-c7b900f38ab7", "John", "Doe", "john.doe@gmail.com", new Date(studentBirthDate),
                "MALE", Role.STUDENT, "myPasswordEncoded");
        mockedParentUser = new User("26b133a6-ea05-447b-904e-4415bfa92061", "James", "Doe", "james.doe@gmail.com", new Date(parentBirthDate),
                "MALE", Role.PARENT, "myPasswordEncoded");

        Supplier<UUID> uuidSupplier = Mockito.mock(Supplier.class);
        try {
            Field uuidSupplierField = UserServiceImpl.class.getDeclaredField("uuidSupplier");
            uuidSupplierField.setAccessible(true);
            uuidSupplierField.set(userService, uuidSupplier);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        List<ParentStudentConnection> parentStudentConnections = new ArrayList<>();
        ParentStudentConnection parentStudentConnection = new ParentStudentConnection(
                "c0f1f032-753b-4dd4-94ac-a32101907cb5", mockedParentUser, mockedUser, new Date(connectionCreationDate)
        );
        parentStudentConnections.add(parentStudentConnection);

        when(uuidSupplier.get()).thenReturn(UUID.fromString("5211e915-c3e2-4dcb-0776-c7b900f38ab7"));
        when(bCryptPasswordEncoder.encode("myPassword")).thenReturn("myPasswordEncoded");
        when(userRepo.findById("5211e915-c3e2-4dcb-0776-c7b900f38ab7")).thenReturn(java.util.Optional.of(mockedUser));
        when(userRepo.save(mockedUser)).thenReturn(mockedUser);
        when(parentStudentConnectionRepo.findByParentId("26b133a6-ea05-447b-904e-4415bfa92061")).thenReturn(parentStudentConnections);
    }

    @Test
    public void getUser_byId_returnUserTransport() {
        assertThat(userService.getUserById("5211e915-c3e2-4dcb-0776-c7b900f38ab7")).isEqualToComparingFieldByField(UserMapper.userToUserTransport(mockedUser));
    }

    @Test
    public void register_byUserRegisterTransport_returnUserTransport() {
        UserRegisterTransport userRegisterTransport = new UserRegisterTransport("john.doe@gmail.com", "John", "Doe", new Date(studentBirthDate),
                "MALE", "STUDENT", "myPassword");
        UserTransport registeredUser = userService.register(userRegisterTransport);
        assertThat(registeredUser).isEqualToComparingFieldByField(UserMapper.userToUserTransport(mockedUser));
    }

    @Test
    public void getChildren_byParentId_returnListOfUserTransport() {
        List<UserTransport> userTransports = userService.getChildrenByParent("26b133a6-ea05-447b-904e-4415bfa92061");
        assertThat(userTransports.size()).isEqualTo(1);
        assertThat(userTransports.get(0)).isEqualToComparingFieldByField(UserMapper.userToUserTransport(mockedUser));
    }

}
