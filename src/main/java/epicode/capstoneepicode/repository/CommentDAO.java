package epicode.capstoneepicode.repository;

import epicode.capstoneepicode.entities.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentDAO extends JpaRepository<Comment, UUID> {
}
