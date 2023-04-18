package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    Optional<User> findUserByUsername(String username);

    void addUser(User user, String[] roles);

    void updateUser(Long id, User user, String[] roles);

    void deleteUser(Long id);

    User getUser(Long id);

    List<User> getUsers();

}
