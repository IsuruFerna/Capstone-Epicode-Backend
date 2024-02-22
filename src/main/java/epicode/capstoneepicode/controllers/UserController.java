package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.user.FollowingFollower;
import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.payload.user.NewUserResponseDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private FollowingFollowerService followingFollowerService;

    // re-consider(which is not relevant) for ADMIN
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

        FollowingFollower otherUserData;
        try {
            otherUserData = followingFollowerService.findByUser(otherUser);
        } catch (NotFoundException ex) {
            otherUserData = new FollowingFollower();
            otherUserData.setUser(otherUser);
            otherUserData.setFollowing(Collections.emptySet());
            otherUserData.setFollowers(Collections.emptySet());
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

    // to search users
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
    public User getProfile(@AuthenticationPrincipal User currentUser)
    {
        User user = userService.findById(currentUser.getId());
        return currentUser;
    }


}
