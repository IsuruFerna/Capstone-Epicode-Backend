package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.user.FollowingFollower;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.payload.followingFollower.FollowUnfollowResponse;
import epicode.capstoneepicode.service.FollowingFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/followUnfollow")
public class FollowingFollowerController {
    @Autowired
    private FollowingFollowerService followingFollowerService;

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public FollowUnfollowResponse followUnfollow(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID userId) throws IOException {
        return followingFollowerService.followUnfollow(currentUser, userId);
    }
}
