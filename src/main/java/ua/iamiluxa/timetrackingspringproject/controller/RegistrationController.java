package ua.iamiluxa.timetrackingspringproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.iamiluxa.timetrackingspringproject.dto.RegistrationDTO;
import ua.iamiluxa.timetrackingspringproject.service.UserService;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String RegistrationPage(@ModelAttribute("user") RegistrationDTO registrationDTO) {
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") @Valid RegistrationDTO registrationDTO,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.registerUser(registrationDTO);

        return "login";
    }


}
