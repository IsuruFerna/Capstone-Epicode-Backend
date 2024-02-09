package epicode.capstoneepicode.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.payload.post.NewPostDTO;
import epicode.capstoneepicode.repository.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class PostService {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Post save(NewPostDTO body) {
        if(body.content().isEmpty() && body.media() == null) {
            throw new BadRequestException("Unable to post content due to empty inputs!");
        }
        Post post = new Post();
        post.setContent(body.content());
        post.setUser(userService.findById(body.userid()));
        post.setMedia(body.media().toString());
        post.setTimeStamp(LocalDateTime.now());
        post.setEdited(false);
        return  postDAO.save(post);
    }

    public Post findById(UUID id) {
        return postDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Post found = this.findById(id);
        postDAO.delete(found);
    }

    public Post findByIdAndUpdate(UUID id, NewPostDTO body) {
        Post found = this.findById(id);
        found.setContent(body.content());
        found.setMedia(body.media().toString());
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
