package epicode.capstoneepicode.service;


import epicode.capstoneepicode.entities.user.FollowingFollower;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.payload.user.NewUserDTO;
import epicode.capstoneepicode.payload.user.NewUserResponseDTO;
import epicode.capstoneepicode.payload.user.UpdateUserDTO;
import epicode.capstoneepicode.repository.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;




    public User findById(UUID id) {
        return userDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username).orElseThrow(()-> new NotFoundException(username));
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException(email));
    }

    public User findByIdAndUpdate(UUID id, UpdateUserDTO body) {

        // handles username, email and password validation
        if(!body.email().isEmpty() || !body.username().isEmpty() || !body.password().isEmpty()) {
            userDAO.findByEmail(body.email()).ifPresent(user -> {
                throw new BadRequestException("Email " + body.email() + " is already used");
            });
            userDAO.findByUsername(body.username()).ifPresent(user -> {
                throw new BadRequestException("Username " + body.username() + " is already used");
            });
            if(body.password() != null && !body.password().equals(body.passwordConfirm())) {
                throw new BadRequestException("Password and Confirmation mismatch");
            }
        }

        User found = this.findById(id);

        // converts birthday to LocalDate format
        if(body.birthDay() != null) {
            try {
                found.setBirthDay(LocalDate.parse(body.birthDay()));
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Invalid birthday format. Please use the format: yyyy-mm-dd");
            }
        }

        found.setFirstName(body.firstName());
        found.setLastName(body.lastName());
        found.setUsername(body.username());
        found.setEmail(body.email());

        return userDAO.save(found);
    }


}
