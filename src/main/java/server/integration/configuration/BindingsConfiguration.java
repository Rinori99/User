package server.integration.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BindingsConfiguration {

    @Value("${binding.simple.email}")
    private String emailDirectBindingKey;

    @Value("${binding.user.new.student}")
    private String newStudentBindingKey;

    @Value("${binding.user.new.teacher}")
    private String newTeacherBindingKey;

    @Value("${binding.user.new.admin}")
    private String newAdminBindingKey;

    @Value("${binding.user.new.parent}")
    private String newParentBindingKey;

    @Value("${binding.user.new.general}")
    private String newUserGeneralBindingKey;

    @Value("${binding.connection.student.parent}")
    private String newStudentParentConnectionBindingKey;


    @Bean
    public Binding bindDirectExchangeToEmailQueue(@Qualifier("emailQueue") Queue emailQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(emailQueue).to(directExchange).with(emailDirectBindingKey);
    }

    @Bean
    public Binding bindDirectExchangeToNewStudentsQueue(@Qualifier("newStudentsQueue") Queue newStudentsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(newStudentsQueue).to(directExchange).with(newStudentBindingKey);
    }

    @Bean
    public Binding bindDirectExchangeToNewParentsQueue(@Qualifier("newParentsQueue") Queue newParentsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(newParentsQueue).to(directExchange).with(newParentBindingKey);
    }

    @Bean
    public Binding bindDirectExchangeToNewTeachersQueue(@Qualifier("newTeachersQueue") Queue newTeachersQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(newTeachersQueue).to(directExchange).with(newTeacherBindingKey);
    }

    @Bean
    public Binding bindDirectExchangeToNewAdminsQueue(@Qualifier("newAdminsQueue") Queue newAdminsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(newAdminsQueue).to(directExchange).with(newAdminBindingKey);
    }

    @Bean
    public Binding bindTopicExchangeToNewGeneralUserQueue(@Qualifier("newGeneralUserQueue") Queue newGeneralUserQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(newGeneralUserQueue).to(topicExchange).with(newUserGeneralBindingKey);
    }

    @Bean
    public Binding bindDirectExchangeToNewStudentParentConnectionQueue(@Qualifier("newStudentParentConnectionQueue") Queue newStudentParentConnectionQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(newStudentParentConnectionQueue).to(directExchange).with(newStudentParentConnectionBindingKey);
    }

}
