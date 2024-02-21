package epicode.capstoneepicode.controllers;



import com.fasterxml.jackson.databind.ObjectMapper;
import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.payload.post.NewMediaResponse;
import epicode.capstoneepicode.payload.post.PostDTO;
import epicode.capstoneepicode.payload.post.NewPostResponse;
import epicode.capstoneepicode.payload.post.ResponsePostDTO;
import epicode.capstoneepicode.repository.PostDAO;
import epicode.capstoneepicode.service.PostService;
import epicode.capstoneepicode.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // this is for the home page
    @GetMapping("")
    public Page<ResponsePostDTO> getPosts(@RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("timeStamp").descending());
        return postService.findALl(pageable);
    }

    // get posts by username
    @GetMapping("/{username}")
    public Page<Post> userPosts(@PathVariable String username, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("timeStamp").descending());
        return postDAO.findPostsByUserid(username, pageable);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Post postPost(@AuthenticationPrincipal User currentUser,
                                    @RequestBody @Validated PostDTO newPostPayload, BindingResult validation) throws IOException {
        System.out.println(validation);
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are Errors in post payload");
        } else {
//            Post newPost = postService.save(currentUser, newPostPayload);
//            return new NewPostResponse(newPost.getId(), newPost.getTimeStamp(), newPost.getEdited());

            return postService.save(currentUser, newPostPayload);
        }
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(
            @AuthenticationPrincipal User currentUser,
            @RequestBody PostDTO payload,
            @PathVariable UUID postId
    ) throws IOException {

        return  postService.findByIdAndUpdate(currentUser, postId, payload);
    }

    @PostMapping("/media")
    @ResponseStatus(HttpStatus.CREATED)
    public NewMediaResponse postMedia(@AuthenticationPrincipal User currentUser,
                                     @RequestParam("media") MultipartFile file
                                     ) throws IOException {
        try {
            Post post = postService.saveMedia(file, currentUser);
            return new NewMediaResponse(post.getMedia(), post.getId());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @PatchMapping("/media/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public NewMediaResponse updatePostMedia(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("media") MultipartFile file,
            @PathVariable UUID postId
    ) throws IOException {

        Post updated = postService.updatePostMedia(file, postId, currentUser);
        return new NewMediaResponse(updated.getMedia(), updated.getId());
    }

    // ************ user can handle his own posts ***************
    @PutMapping("/{postId}")
    public NewPostResponse editPost(@AuthenticationPrincipal User currentUser,
                                    @PathVariable UUID postId,
                                    @RequestBody @Validated PostDTO updatePostPayload, BindingResult validation) throws IOException {
        System.out.println(validation);
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are Errors in post payload");
        } else {
            Post updated = postService.findByIdAndUpdate(currentUser, postId, updatePostPayload);
            return new NewPostResponse(updated.getId(), updated.getTimeStamp(), updated.getEdited());
        }
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@AuthenticationPrincipal User currentUser,
                           @PathVariable UUID postId) throws IOException {
            postService.findByIdAndDelete(currentUser, postId);
    }










}
