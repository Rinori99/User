package server.integration.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.integration.models.SerializableParentStudentConnection;

@Component
public class StudentParentConnectionProducer {

    @Value("${exchange.direct}")
    private String directExchange;

    @Value("${routing.connection.student.parent}")
    private String parentStudentConnectionRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    public StudentParentConnectionProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendParentStudentConnection(SerializableParentStudentConnection connection) {
        rabbitTemplate.convertAndSend(directExchange, parentStudentConnectionRoutingKey, connection);
    }

}
