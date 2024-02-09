package epicode.capstoneepicode.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
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

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
