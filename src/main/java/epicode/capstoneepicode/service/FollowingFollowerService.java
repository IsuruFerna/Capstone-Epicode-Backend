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

import java.util.Collections;
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
        // if there's no any instance(happens at the first time), create one
        FollowingFollower currentUserData;
        try {
            currentUserData = this.findByUser(currntUser);
        } catch (NotFoundException ex) {
            currentUserData = new FollowingFollower();
            currentUserData.setUser(currntUser);
        }

        Boolean isFollowing = currentUserData.addToFollowing(userToFollow);


        // sets to follow user's followers list
        FollowingFollower userToFollowData;
        try {
            userToFollowData = this.findByUser(userToFollow);
        } catch (NotFoundException ex) {
            userToFollowData = new FollowingFollower();
            userToFollowData.setUser(userToFollow);
        }
        userToFollowData.addToFollowers(loggedUser);

        // saves into current user following and toFollow user's followers lists
        followingFollowerDAO.save(currentUserData);
        followingFollowerDAO.save(userToFollowData);

        return new FollowUnfollowResponse(
                isFollowing,
                userToFollowData.getFollowing().size(),
                userToFollowData.getFollowers().size());
    }
}
