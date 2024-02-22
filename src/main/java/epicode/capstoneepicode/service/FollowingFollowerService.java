package epicode.capstoneepicode.service;

import epicode.capstoneepicode.entities.user.FollowingFollower;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.exceptions.UnauthorizedException;
import epicode.capstoneepicode.payload.followingFollower.FollowUnfollowResponse;
import epicode.capstoneepicode.repository.FollowingFollowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FollowingFollowerService {

    @Autowired
    private FollowingFollowerDAO followingFollowerDAO;

    @Autowired
    private UserService userService;

    public FollowingFollower findById(UUID id) {
        return followingFollowerDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public FollowingFollower findByUser(User user) {
        return followingFollowerDAO.findByUser(user).orElseThrow(()-> new NotFoundException(user.getId()));
    }

    public FollowUnfollowResponse followUnfollow(User currntUser, UUID userId) {
        // logged user data
        User loggedUser = userService.findById(currntUser.getId());
        User userToFollow = userService.findById(userId);

        if(loggedUser.equals(userToFollow)) {
            throw new BadRequestException("User can't follow himself!");
        }

        // sets current user following list
        FollowingFollower currentUserData = this.findByUser(currntUser);

        if(!loggedUser.equals(currentUserData.getUser())) {
            throw new UnauthorizedException("User have no permission to follow/unfollow action");
        }

        currentUserData.addToFollowing(userToFollow);
        Boolean isFollowing = currentUserData.addToFollowing(userToFollow);

        // sets to follow user's followers list
        FollowingFollower useToFollowData = this.findByUser(userToFollow);
        useToFollowData.addToFollowers(loggedUser);

        // saves into current user following and toFollow user's followers lists
        followingFollowerDAO.save(currentUserData);
        followingFollowerDAO.save(useToFollowData);

        return new FollowUnfollowResponse(
                isFollowing,
                useToFollowData.getFollowing().size(),
                useToFollowData.getFollowers().size());
    }
}
