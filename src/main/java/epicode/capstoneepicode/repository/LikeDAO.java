package epicode.capstoneepicode.repository;

import epicode.capstoneepicode.entities.post.Like;
import epicode.capstoneepicode.entities.post.Post;
import epicode.capstoneepicode.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LikeDAO extends JpaRepository<Like, UUID> {
    Optional<Like> findByPost(Post post);
}
