package server.integration.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.integration.models.SerializableUser;
import server.models.Role;
import server.models.User;

@Component
public class UserProducer {

    @Value("${routing.user.new.student}")
    private String newStudentRoutingKey;

    @Value("${routing.user.new.teacher}")
    private String newTeacherRoutingKey;

    @Value("${routing.user.new.parent}")
    private String newParentRoutingKey;

    @Value("${routing.user.new.admin}")
    private String newAdminRoutingKey;

    @Value("${exchange.topic}")
    private String topicExchange;

    private final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNewUser(User user) {
        switch (user.getRole()) {
            case STUDENT:
                sendNewStudent(user);
                break;
            case TEACHER:
                sendNewTeacher(user);
                break;
            case PARENT:
                sendNewParent(user);
                break;
            case SCHOOL_ADMIN:
                sendNewAdmin(user);
                break;
        }
    }

    public void sendNewStudent(User user) {
        SerializableUser serializableUser = getSerializableUserFromUser(user);
        rabbitTemplate.convertAndSend(topicExchange, newStudentRoutingKey, serializableUser);
    }

    public void sendNewTeacher(User user) {
        SerializableUser serializableUser = getSerializableUserFromUser(user);
        rabbitTemplate.convertAndSend(topicExchange, newTeacherRoutingKey, serializableUser);
    }

    public void sendNewParent(User user) {
        SerializableUser serializableUser = getSerializableUserFromUser(user);
        rabbitTemplate.convertAndSend(topicExchange, newParentRoutingKey, serializableUser);
    }

    public void sendNewAdmin(User user) {
        SerializableUser serializableUser = getSerializableUserFromUser(user);
        rabbitTemplate.convertAndSend(topicExchange, newAdminRoutingKey, serializableUser);
    }

    private SerializableUser getSerializableUserFromUser(User user) {
        return new SerializableUser(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getPassword());
    }

}
