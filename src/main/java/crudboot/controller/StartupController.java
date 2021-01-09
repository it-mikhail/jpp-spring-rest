package crudboot.controller;

import crudboot.model.Role;
import crudboot.model.User;
import crudboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class StartupController {
    final private UserService userService;

    @Autowired
    public StartupController(UserService userService) {
        this.userService = userService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // for testing, "preinstalling" roles and users

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");

        Role testRole = new Role();
        testRole.setRole("ROLE_TEST");

        userService.addRole(userRole);
        userService.addRole(adminRole);
        userService.addRole(testRole);

        Set<Role> userRolesSet = new HashSet<>();
        Set<Role> adminRolesSet = new HashSet<>();

        userRolesSet.add(userRole);

        adminRolesSet.add(userRole);
        adminRolesSet.add(adminRole);

        User user = new User("userFirstName", "userLastName", "user@localhost", 35,"user", userRolesSet);
        User admin = new User("adminFirstName", "adminLastName", "admin@localhost", 35, "admin", adminRolesSet);

        userService.add(user);
        userService.add(admin);

        userService.add(new User("user1", "sidorov", "user1@localhost", 15, "123", userRolesSet));
        userService.add(new User("user2", "sidorov", "user2@localhost", 16, "123", userRolesSet));
        userService.add(new User("user3", "sidorov", "user3@localhost", 17, "123", userRolesSet));
        userService.add(new User("user4", "sidorov", "user4@localhost", 18, "123", userRolesSet));
        userService.add(new User("user5", "sidorov", "user5@localhost", 19, "123", userRolesSet));

    }

}
