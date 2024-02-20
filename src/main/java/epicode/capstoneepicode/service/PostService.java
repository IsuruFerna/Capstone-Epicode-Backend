package epicode.capstoneepicode.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.exceptions.UnauthorizedException;
import epicode.capstoneepicode.payload.post.PostDTO;
import epicode.capstoneepicode.payload.post.ResponsePostDTO;
import epicode.capstoneepicode.repository.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

        // check if there's an Image in the post
        Post post;
        if(body.postId() != null) {
            post = this.findById(body.postId());

            System.out.println("found the media");
        } else {
            post = new Post();
            post.setUser(u);
            post.setTimeStamp(LocalDateTime.now());
            post.setEdited(false);
        }

        post.setContent(body.content());

        return  postDAO.save(post);
    }

    public Post findById(UUID id) {
        return postDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Page<ResponsePostDTO> findALl(Pageable pageable) {
        Page<Post> pagePosts = postDAO.findAll(pageable);
        List<ResponsePostDTO> posts = pagePosts.stream().map(post ->
         new ResponsePostDTO(
                    post.getId(),
                    post.getContent(),
                    post.getMedia(),
                    post.getTimeStamp(),
                    post.getEdited(),
                    post.getUser().getUsername(),
                    post.getUser().getFirstName(),
                    post.getUser().getLastName()
            )
        ).toList();

        return new PageImpl<>(posts, pageable, pagePosts.getTotalElements());
    }

    public void findByIdAndDelete(User user, UUID id) {

        Post found = this.findById(id);
        User u = userService.findById(user.getId());

        // check user permissions
        if(!found.getUser().equals(u)) {
            throw new UnauthorizedException("User have no permission to delete this post!");
        }
        postDAO.delete(found);
    }

    public Post findByIdAndUpdate(User user, UUID id, PostDTO body) {
        User u = userService.findById(user.getId());
        Post found = this.findById(id);

        // checks if the user trying to modify is one of his own posts or not
        if(!found.getUser().equals(u)) {
            throw new UnauthorizedException("User has no permission to edit this post: " + id);
        }

        found.setMedia(body.media());
        found.setContent(body.content());
        found.setEdited(true);
        return postDAO.save(found);
    }

    public Post saveMedia(MultipartFile file, User user) throws IOException {
        if(file.isEmpty()) {
            throw new BadRequestException("Unable to post content due to empty inputs!");
        }

        // solved some issues instead of passing directly the user
        User u = userService.findById(user.getId());

        Post post = new Post();
        post.setUser(u);
        post.setTimeStamp(LocalDateTime.now());
        post.setEdited(false);

        Map<String, Object> uploadResult = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String imageURL =  uploadResult.get("url").toString();
        String imagePubicId = uploadResult.get("public_id").toString();

        post.setImagePublicId(imagePubicId);
        post.setMedia(imageURL);

        return  postDAO.save(post);
    }

    public Post deleteMedia(User user, UUID postId) throws IOException {
        Post post = this.findById(postId);
        User u = userService.findById(user.getId());

        // delete the image
        Map destroyResult = cloudinaryUploader.uploader().destroy(post.getImagePublicId(), ObjectUtils.emptyMap());

        if("ok".equals(destroyResult.get("result"))) {
            System.out.println("image deleted " + post);
            post.setImagePublicId(null);
            post.setMedia(null);

        } else {
            System.out.println("image does not deleted " + post);
        }

        return postDAO.save(post);
    }
}
