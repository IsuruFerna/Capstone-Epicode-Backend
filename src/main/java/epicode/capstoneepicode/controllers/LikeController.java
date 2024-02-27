package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.post.Like;
import epicode.capstoneepicode.entities.post.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.payload.post.LikeResponse;
import epicode.capstoneepicode.repository.LikeDAO;
import epicode.capstoneepicode.repository.PostDAO;
import epicode.capstoneepicode.service.PostService;
import epicode.capstoneepicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private LikeDAO likeDAO;


    @PutMapping("/{postId}")
    public LikeResponse likePost(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID postId
            ) throws IOException {
        User user = userService.findById(currentUser.getId());
        Post post = postService.findById(postId);

        Like newLike = new Like();
        Boolean isLiked;

        // set new Like instance if there aren't
        try {
            isLiked = post.getLike().addLike(user);
        } catch (NullPointerException ex) {
            newLike.setPost(post);
            post.setLike(newLike);
            isLiked = post.getLike().addLike(user);
        }

        // set like count on the post. it's good for performance in large scale in this way
        if (isLiked) {
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
            post.setLikeCount(post.getLikeCount() - 1);
        }
        postDAO.save(post);
        likeDAO.save(newLike);

        return new LikeResponse(isLiked, post.getLikeCount());
    }
}
