package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.payload.user.NewUserDTO;
import epicode.capstoneepicode.payload.user.NewUserResponseDTO;
import epicode.capstoneepicode.payload.user.UserLoginDTO;
import epicode.capstoneepicode.payload.user.UserLoginResponseDTO;
import epicode.capstoneepicode.service.AuthService;
import epicode.capstoneepicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        String accessToken = authService.authenticateUser(body);
        User user = userService.findByEmail(body.email());
        return new UserLoginResponseDTO(accessToken, user.getId());
    }

    @PostMapping("/register")
    public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation) throws IOException {
        System.out.println(validation);
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are Errors in register payload!");
        } else {
            User newUser = authService.save(newUserPayload);
            return new NewUserResponseDTO(newUser.getId());
        }
    }
}
