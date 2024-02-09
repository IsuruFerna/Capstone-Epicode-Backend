package epicode.capstoneepicode.service;

import epicode.capstoneepicode.entities.user.Role;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.UnauthorizedException;
import epicode.capstoneepicode.payload.user.NewUserDTO;
import epicode.capstoneepicode.payload.user.NewUserResponseDTO;
import epicode.capstoneepicode.payload.user.UserLoginDTO;
import epicode.capstoneepicode.repository.UserDAO;
import epicode.capstoneepicode.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) {
        User user = userService.findByEmail(body.email());

        if(bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Not valid credentials!");
        }
    }

    public User save(NewUserDTO body) throws IOException {
        userDAO.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("Email " + body.email() + " is already used");
        });
        userDAO.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("Username " + body.username() + " is already used");
        });
        if(!body.password().equals(body.passwordConfirm())) {
            throw new BadRequestException("Password and Confirmation mismatch");
        }

        LocalDate birthDay;
        try {
            birthDay = LocalDate.parse(body.birthDay());
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid birthday format. Please use the format: yyyy-mm-dd");
        }

        // TO-DO: handle password validation

        User newUser = new User();
        newUser.setProfilePicture("https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.lastName());
        newUser.setFirstName(body.firstName());
        newUser.setLastName(body.lastName());
        newUser.setUsername(body.username());
        newUser.setEmail(body.email());
        newUser.setBirthDay(birthDay);
        newUser.setRole(Role.USER);
        newUser.setPassword(bcrypt.encode(body.password()));


        return userDAO.save(newUser);
    }
}
