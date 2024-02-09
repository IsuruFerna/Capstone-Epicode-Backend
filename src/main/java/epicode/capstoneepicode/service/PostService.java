package epicode.capstoneepicode.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.exceptions.UnauthorizedException;
import epicode.capstoneepicode.payload.post.PostDTO;
import epicode.capstoneepicode.repository.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Post save(User user, PostDTO body) {
        if(body.content().isEmpty() && body.media() == null) {
            throw new BadRequestException("Unable to post content due to empty inputs!");
        }

        // solved some issues instead of passing directly the user
        User u = userService.findById(user.getId());

        Post post = new Post();
        post.setContent(body.content());
        post.setUser(u);
        post.setMedia(body.media());
        post.setTimeStamp(LocalDateTime.now());
        post.setEdited(false);
        return  postDAO.save(post);
    }

    public Post findById(UUID id) {
        return postDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public void findByIdAndDelete(User user, UUID id) {

        Post found = this.findById(id);
        User u = userService.findById(user.getId());

        if(!found.getUser().equals(u)) {
            throw new UnauthorizedException("User have no permission to delete this post!");
        }
        postDAO.delete(found);
    }

    public Post findByIdAndUpdate(User user, UUID id, PostDTO body) {
        User u = userService.findById(user.getId());
        Post found = this.findById(id);

        // checks if the user trying to modify is one of his posts or not
        if(!found.getUser().equals(u)) {
            throw new UnauthorizedException("User has no permission to edit post: " + id);
        }

        found.setMedia(body.media());
        found.setContent(body.content());
        found.setEdited(true);
        return postDAO.save(found);
    }

    public Post uploadImage(UUID id, MultipartFile file) throws IOException {
        Post found = this.findById(id);
        String imageURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setMedia(imageURL);
        return postDAO.save(found);
    }
}
