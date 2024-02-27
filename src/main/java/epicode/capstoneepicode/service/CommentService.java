package epicode.capstoneepicode.service;

import epicode.capstoneepicode.entities.post.Comment;
import epicode.capstoneepicode.entities.post.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.exceptions.UnauthorizedException;
import epicode.capstoneepicode.payload.post.NewCommentDAO;
import epicode.capstoneepicode.repository.CommentDAO;
import epicode.capstoneepicode.repository.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private PostService postService;

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserService userService;

    public Comment findById(UUID id) {
        return commentDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public void deleteComment(User currentUser, UUID commentId) {
        User user = userService.findById(currentUser.getId());
        Comment comment = this.findById(commentId);
        Post post = comment.getPost();

        if(!comment.getUser().equals(user) || !post.getUser().equals(user)) {
            throw new UnauthorizedException("User have no permission to delete this comment!");
        }
        commentDAO.delete(comment);
    }

    public Comment save(User currentUser, UUID postId, NewCommentDAO body) {
        Post post = postService.findById(postId);
        User user = userService.findById(currentUser.getId());

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setComment(body.comment());
        comment.setUser(user);
        comment.setTimeStamp(LocalDateTime.now());

        post.setCommentsCount(post.getCommentsCount() + 1);
        postDAO.save(post);

        return commentDAO.save(comment);
    }
}
