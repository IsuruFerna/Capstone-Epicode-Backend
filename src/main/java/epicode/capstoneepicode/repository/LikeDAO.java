package epicode.capstoneepicode.repository;

import epicode.capstoneepicode.entities.post.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeDAO extends JpaRepository<Like, UUID> {
}
