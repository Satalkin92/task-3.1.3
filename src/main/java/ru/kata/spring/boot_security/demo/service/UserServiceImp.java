package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void addUser(User user, String[] roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        addRolesByUser(user, roles);
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User user, String[] roles) {
        user.setId(id);
        addRolesByUser(user, roles);
        userRepository.save(user);
    }

    @Override
    public void addRolesByUser(User user, String[] roles) {
        List<Role> role = new ArrayList<>();
        for (String s : roles) {
            List<Role> list = (Collections.singletonList(roleRepository.findRoleByName(s)));
            if (list.isEmpty()) {
                role.add(new Role(s));
            } else {
                role.addAll(list);

            }
        }
        user.setRoles(role);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(FindException::new);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
