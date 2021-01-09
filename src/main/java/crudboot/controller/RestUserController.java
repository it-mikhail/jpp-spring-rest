package crudboot.controller;

import crudboot.model.User;
import crudboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestUserController {

    final private UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin/users")
    public List<User> getUsersList() {
        return userService.getUsersList();
    }

    @GetMapping(value = "/admin/user/{id}")
    public User getUserData(@PathVariable String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @PostMapping(value = "/admin/users", consumes = "application/json")
    public User addNewUser(@RequestBody User user) {
        userService.add(user);
        return userService.getUserByEmail(user.getEmail());
    }

    @PutMapping(value = "/admin/user/{id}")
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

    @DeleteMapping(value = "/admin/user/{id}")
    public void deleteUser(@RequestBody User user) {
        userService.delete(user);
    }
}
