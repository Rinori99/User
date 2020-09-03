package server.services;

import server.DTOs.UserRegisterTransport;
import server.DTOs.UserTransport;

import java.util.List;

public interface UserService {

    UserTransport getUserById(String id);

    UserTransport register(UserRegisterTransport userRegisterTransport);

    List<UserTransport> getChildrenByParent(String parentId);

    void updateUser();

}
