package server.services;

import server.DTOs.UserRegisterTransport;
import server.DTOs.UserTransport;
import server.models.User;

import java.util.Date;
import java.util.List;

public interface UserService {

    UserTransport getUserById(String id);

    void register(UserRegisterTransport userRegisterTransport);

    List<UserTransport> getChildrenByParent(String parentId);

}
