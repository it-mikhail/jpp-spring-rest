package crudboot.service;

import crudboot.model.Role;
import crudboot.repository.RoleRepository;
import crudboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import crudboot.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(User user) {
        userRepository.save(updateUserRoles(user));
    }

    @Override
    public void update(User user) {
        userRepository.save(updateUserRoles(user));
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> getUsersList() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User getUserByName(String userEmail) {
        return this.getUserByEmail(userEmail);
    }

    @Override
    public User getUserByEmail(String userEmail) { return userRepository.getUserByEmail(userEmail); }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getAvailableRoles() {
        return roleRepository.findAll();
    }

    @Override
    public User updateUserRoles(User user) {
        boolean updateRoles = false;
        Set<Role> rolesUpdateSet = new HashSet<>();

        for (Role role : user.getRoles()) {
            if (role.getId() == null) {
                updateRoles = true;
                rolesUpdateSet.add(roleRepository.getRoleByName(role.getRole()));
            } else {
                rolesUpdateSet.add(role);
            }
        }

        if (updateRoles) {
            user.setRoles(rolesUpdateSet);
        }

        return user;
    }

}
