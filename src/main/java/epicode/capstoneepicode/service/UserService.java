package epicode.capstoneepicode.service;


import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.repository.UserDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User findById(UUID id) {
        return userDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public User findByEmail(String email) {
        return userDAO.findByEmail(email).orElseThrow(()-> new NotFoundException(email));
    }


}
