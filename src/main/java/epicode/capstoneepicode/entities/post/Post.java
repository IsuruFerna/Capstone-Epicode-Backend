package epicode.capstoneepicode.entities.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import epicode.capstoneepicode.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private int likeCount;
    private int commentsCount;


    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    private Like like;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
