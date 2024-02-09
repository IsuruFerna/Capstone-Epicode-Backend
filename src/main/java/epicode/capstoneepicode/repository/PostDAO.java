package epicode.capstoneepicode.repository;

import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostDAO extends JpaRepository<Post, UUID> {
//    Page<Post> findByUser(User user, Pageable pageable);

    @Query("SELECT p FROM Post p Join p.user u WHERE u.id = :userId")
    Page<Post> findPostsByUserid(@Param("userId") UUID userId, Pageable pageable);
}
