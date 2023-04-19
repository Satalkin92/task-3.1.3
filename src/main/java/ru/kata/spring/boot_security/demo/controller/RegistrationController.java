package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String getStartPage() {
        return "index";
    }


    @PostMapping()
    public String registrationUser(@ModelAttribute("user") @Valid User user, @RequestParam("rolesList") String[] roles,
                          BindingResult br) {
        if (br.hasErrors()) {
            return "registration";
        }
        userService.addUser(user, roles);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registrationForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "registration";
    }

}
