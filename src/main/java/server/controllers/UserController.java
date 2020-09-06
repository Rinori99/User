package server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.DTOs.UserRegisterTransport;
import server.DTOs.UserTransport;
import server.PerRequestIdStorage;
import server.services.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends BaseController{

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserTransport getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("parents/children")
    public List<UserTransport> getChildrenByParent() {
        return userService.getChildrenByParent(PerRequestIdStorage.getUserId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void register(@RequestBody UserRegisterTransport userRegisterTransport) {
        userService.register(userRegisterTransport);
    }

}
