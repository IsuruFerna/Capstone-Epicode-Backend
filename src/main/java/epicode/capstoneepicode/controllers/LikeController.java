package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.post.Like;
import epicode.capstoneepicode.entities.post.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.payload.post.LikeResponse;
import epicode.capstoneepicode.repository.LikeDAO;
import epicode.capstoneepicode.repository.PostDAO;
import epicode.capstoneepicode.service.LikeService;
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

    @Autowired
    private LikeService likeService;


    @PutMapping("/{postId}")
    public LikeResponse likePost(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID postId
            ) throws IOException {
        User user = userService.findById(currentUser.getId());
        Post post = postService.findById(postId);

        // set new Like instance if there aren't
        Like like = likeDAO.findByPost(post).orElse(null);
        if(like == null) {
            like = new Like();
            like.setPost(post);
            post.setLike(like);
        }

        Boolean isLiked = like.addLike(user);

        // set like count on the post. it's good for performance in large scale in this way
        if (isLiked) {
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
            post.setLikeCount(post.getLikeCount() - 1);
        }
        postDAO.save(post);
        likeDAO.save(like);

        return new LikeResponse(isLiked, post.getLikeCount());
    }
}
