package epicode.capstoneepicode.entities.post;

import epicode.capstoneepicode.entities.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToMany
    @JoinTable(
            name = "liked_users",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "liked_user_id")
    )
    private Set<User> likedUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "dislike_users",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "dislike_user_id")
    )
    private Set<User> dislikedUsers = new HashSet<>();

    public Boolean addLike(User user) {
        if(!this.likedUsers.contains(user)) {
            this.likedUsers.add(user);
            return true;
        } else {
            this.likedUsers.remove(user);
            return false;
        }
    }


}
