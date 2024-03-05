package epicode.capstoneepicode.repository;

import epicode.capstoneepicode.entities.user.FollowingFollower;
import epicode.capstoneepicode.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FollowingFollowerDAO extends JpaRepository<FollowingFollower, UUID> {

    Optional<FollowingFollower> findByUser(User user);

    @Query("SELECT f FROM FollowingFollower f JOIN f.user u WHERE u.id = :userId")
    Optional<FollowingFollower> findByUserId(@Param("userId") UUID userId);

}
