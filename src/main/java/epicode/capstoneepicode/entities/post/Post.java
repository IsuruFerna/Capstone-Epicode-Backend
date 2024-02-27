package epicode.capstoneepicode.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import epicode.capstoneepicode.entities.post.Like;
import epicode.capstoneepicode.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(length = 500)
    private String content;
    private String media;
    private LocalDateTime timeStamp;
    private Boolean edited;
    private String imagePublicId;

    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Like like;
}
