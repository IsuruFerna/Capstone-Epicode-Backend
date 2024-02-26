package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.user.FollowingFollower;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.payload.followingFollower.FollowUnfollowResponse;
import epicode.capstoneepicode.service.FollowingFollowerService;
import epicode.capstoneepicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/followUnfollow")
public class FollowingFollowerController {
    @Autowired
    private FollowingFollowerService followingFollowerService;

    @Autowired
    private UserService userService;

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public FollowUnfollowResponse followUnfollow(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID userId) throws IOException {
        return followingFollowerService.followUnfollow(currentUser, userId);
    }

    @GetMapping("/followBack")
    public List<User> userFollowBack(
            @AuthenticationPrincipal User currentUser
    ) throws IOException {
        return followingFollowerService.userFollowsBack(currentUser);
    }
}
