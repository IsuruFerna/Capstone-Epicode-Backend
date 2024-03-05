package epicode.capstoneepicode.payload.followingFollower;

public record FollowUnfollowResponse(
        Boolean isFollowing,
        Integer following,
        Integer followers
) {
}
