package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.user.FollowingFollower;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.payload.user.LoggedUserResponse;
import epicode.capstoneepicode.payload.user.UpdateUserDTO;
import epicode.capstoneepicode.payload.user.UserResponse;
import epicode.capstoneepicode.repository.UserDAO;
import epicode.capstoneepicode.service.FollowingFollowerService;
import epicode.capstoneepicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private FollowingFollowerService followingFollowerService;

    @GetMapping("")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "username") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userDAO.findAll(pageable);
    }

    @GetMapping("/user/{username}")
    public UserResponse getUser(
            @PathVariable String username,
            @AuthenticationPrincipal User currentUser
    ) {
        User loggedUser = userService.findById(currentUser.getId());
        User otherUser = userService.findByUsername(username);

        // if there's no FollowingFollower instance for the user we are looking,
        // creates a new instance
        FollowingFollower otherUserData;
        try {
            otherUserData = followingFollowerService.findByUser(otherUser);
        } catch (NotFoundException ex) {
            otherUserData = new FollowingFollower();
            otherUserData.setUser(otherUser);
        }
        return new UserResponse(
                otherUser.getId(),
                otherUser.getFirstName(),
                otherUser.getLastName(),
                otherUser.getProfilePicture(),
                otherUser.getRole().toString(),
                otherUser.getUsername(),
                otherUser.getPostList().size(),
                otherUserData.getFollowing().size(),
                otherUserData.getFollowers().size(),
                otherUserData.getFollowers().contains(loggedUser)
        );
    }

    // search any user
    @GetMapping("/{username}")
    public Page<User> getUsersByUsername(@PathVariable String username, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "username") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return userDAO.findUsersByUsername(username, pageable);
    }

    // ************ user self access ************
    @PutMapping("/me")
    public User getMeAndUpdate(@AuthenticationPrincipal User currentUser, @RequestBody @Validated UpdateUserDTO body, BindingResult validation) throws IOException {
        System.out.println(validation);
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are errors in user update payload");
        } else {
            return userService.findByIdAndUpdate(currentUser.getId(), body);
        }
    }

    @GetMapping("/me")
    public LoggedUserResponse getProfile(@AuthenticationPrincipal User currentUser)
    {
        User user = userService.findById(currentUser.getId());

        // if there's no FollowingFollower instance fot the user we are looking,
        // creates a new instance
        FollowingFollower userData;
        try {
            userData = followingFollowerService.findByUser(user);
        } catch (NotFoundException ex) {
            userData = new FollowingFollower();
            userData.setUser(user);
        }

        return new LoggedUserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfilePicture(),
                user.getRole().toString(),
                user.getUsername(),
                user.getPostList().size(),
                userData.getFollowing().size(),
                userData.getFollowers().size(),
                user.getBirthDay(),
                user.getEmail()
        );
    }


}
