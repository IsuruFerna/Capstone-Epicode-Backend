package epicode.capstoneepicode.repository;

import epicode.capstoneepicode.entities.user.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostDAO extends JpaRepository<Post, UUID> {
}
