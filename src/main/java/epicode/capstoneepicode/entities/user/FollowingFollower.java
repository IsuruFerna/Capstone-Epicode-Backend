package epicode.capstoneepicode.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowingFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "following",
            joinColumns = @JoinColumn(name= "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> following;

    @ManyToMany
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private Set<User> followers;

    public Boolean addToFollowing (User toFollow) {
        if(!this.following.contains(toFollow)) {
            this.following.add(toFollow);
            System.out.println("follows");
            return true;
        } else {
            this.following.remove(toFollow);
            System.out.println("unFollows");
            return false;
        }
    }

    public Boolean addToFollowers (User follow) {
        if(!this.followers.contains(follow)) {
            this.followers.add(follow);
            System.out.println("add to followers");
            return true;
        } else {
            this.followers.remove(follow);
            System.out.println("remove form followers");
            return false;
        }
    }

}
