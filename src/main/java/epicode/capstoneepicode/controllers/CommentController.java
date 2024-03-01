package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.post.Comment;
import epicode.capstoneepicode.entities.post.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.payload.post.CommentResponse;
import epicode.capstoneepicode.payload.post.NewCommentDAO;
import epicode.capstoneepicode.service.CommentService;
import epicode.capstoneepicode.service.PostService;
import epicode.capstoneepicode.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    // set later to Page
    @GetMapping("/{postId}")
    public List<CommentResponse> getComments (@PathVariable UUID postId) {
        Post post = postService.findById(postId);
        return post.getComments().stream().map(comment ->
             new CommentResponse(
                    comment.getId(),
                    comment.getComment(),
                    comment.getTimeStamp(),
                    comment.getUser().getFirstName(),
                    comment.getUser().getLastName(),
                     comment.getUser().getUsername()
             )
        ).toList();
    }

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID postId,
            @RequestBody @Validated NewCommentDAO payload,
            BindingResult validation) throws IOException {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are Errors in payload");
        } else {
            Comment saved = commentService.save(currentUser, postId, payload);
            return new CommentResponse(
                    saved.getId(),
                    saved.getComment(),
                    saved.getTimeStamp(),
                    saved.getUser().getFirstName(),
                    saved.getUser().getLastName(),
                    saved.getUser().getUsername()
            );
        }
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @AuthenticationPrincipal User currentUser,
            @PathVariable UUID commentId
    ) throws IOException {
        commentService.deleteComment(currentUser, commentId);
    }
}
