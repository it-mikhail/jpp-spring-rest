package crudboot.service;

import crudboot.model.Role;
import crudboot.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void update(User user);
    void delete(User user);
    List<User> getUsersList();

    User getUserById(Long userId);

    void addRole(Role role);
    User getUserByName(String userEmail);
    User getUserByEmail(String userEmail);

    User updateUserRoles(User user);

    List<Role> getAvailableRoles();

}
