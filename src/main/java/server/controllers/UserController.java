package server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.DTOs.UserRegisterTransport;
import server.DTOs.UserTransport;
import server.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserTransport getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/parents/{parentId}/children/")
    public List<UserTransport> getChildrenByParent(@PathVariable String parentId) {
        return userService.getChildrenByParent(parentId);
    }

    @PostMapping("/")
    public void register(@RequestBody UserRegisterTransport userRegisterTransport) {
        userService.register(userRegisterTransport);
    }

}
