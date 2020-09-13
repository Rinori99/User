package server;

import com.blueveery.springrest2ts.Rest2tsGenerator;
import com.blueveery.springrest2ts.converters.JacksonObjectMapper;
import com.blueveery.springrest2ts.converters.ModelClassesToTsInterfacesConverter;
import com.blueveery.springrest2ts.converters.SpringRestToTsConverter;
import com.blueveery.springrest2ts.filters.ExtendsJavaTypeFilter;
import com.blueveery.springrest2ts.implgens.Angular4ImplementationGenerator;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.DTOs.BaseTransport;
import server.controllers.BaseController;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

@EnableRabbit
@EnableEurekaClient
@SpringBootApplication
public class UserApplication extends SpringBootServletInitializer {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(UserApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        generateApiTsFile();
    }

    private static void generateApiTsFile() {
        Rest2tsGenerator tsGenerator = new Rest2tsGenerator();

        // Java Classes filtering
        tsGenerator.setModelClassesCondition(new ExtendsJavaTypeFilter(BaseTransport.class));
        tsGenerator.setRestClassesCondition(new ExtendsJavaTypeFilter(BaseController.class));

        // Java model classes converter setup
        JacksonObjectMapper jacksonObjectMapper = new JacksonObjectMapper();
        jacksonObjectMapper.setFieldsVisibility(JsonAutoDetect.Visibility.ANY);
        ModelClassesToTsInterfacesConverter modelClassesConverter = new ModelClassesToTsInterfacesConverter(jacksonObjectMapper);
        tsGenerator.setModelClassesConverter(modelClassesConverter);

        // Spring REST controllers converter
        SpringRestToTsConverter restClassesConverter = new SpringRestToTsConverter(new Angular4ImplementationGenerator());
        tsGenerator.setRestClassesConverter(restClassesConverter);

        // set of java root packages for class scanning
        Set<String> javaPackageSet = Collections.singleton("server");
        try {
            tsGenerator.generate(javaPackageSet, Paths.get("./target/generated-sources/api-ts"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}