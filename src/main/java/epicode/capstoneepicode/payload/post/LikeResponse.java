package epicode.capstoneepicode.payload.post;

public record LikeResponse(
        Boolean isLiked,
        Integer likeCount
) {
}
