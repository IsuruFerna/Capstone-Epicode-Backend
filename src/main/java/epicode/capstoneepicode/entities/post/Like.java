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
    private Set<User> users = new HashSet<>();

    public Boolean addLike(User user) {
        if(!this.users.contains(user)) {
            this.users.add(user);
            return true;
        } else {
            this.users.remove(user);
            return false;
        }
    }
}
