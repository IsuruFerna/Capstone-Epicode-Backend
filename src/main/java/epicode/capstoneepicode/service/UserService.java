package epicode.capstoneepicode.service;


import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.payload.NewUserDTO;
import epicode.capstoneepicode.payload.NewUserResponseDTO;
import epicode.capstoneepicode.repository.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User findById(UUID id) {
        return userDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public NewUserResponseDTO save(NewUserDTO body) throws IOException {
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
        newUser.setPassword(body.password());

        userDAO.save(newUser);
        return new NewUserResponseDTO(newUser.getId());
    }
}
