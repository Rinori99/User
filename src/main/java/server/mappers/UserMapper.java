package server.mappers;

import server.DTOs.UserTransport;
import server.models.User;

public class UserMapper {

    public static UserTransport userToUserTransport(User user) {
        return new UserTransport(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthDate(), user.getGender(),
                user.getRole().toString());
    }

}
