package epicode.capstoneepicode.controllers;



import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.entities.user.User;
import epicode.capstoneepicode.exceptions.BadRequestException;
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

import java.io.IOException;
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

    // get posts by user id
    @GetMapping("/{userId}")
    public Page<Post> userPosts(@PathVariable UUID userId, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 20, Sort.by("timeStamp").descending());
        return postDAO.findPostsByUserid(userId, pageable);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public NewPostResponse postPost(@AuthenticationPrincipal User currentUser,
                                    @RequestBody @Validated PostDTO newPostPayload, BindingResult validation) throws IOException {
        System.out.println(validation);
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are Errors in post payload");
        } else {
            Post newPost = postService.save(currentUser, newPostPayload);
            return new NewPostResponse(newPost.getId());
        }
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
            return new NewPostResponse(updated.getId());
        }
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@AuthenticationPrincipal User currentUser,
                           @PathVariable UUID postId) throws IOException {
            postService.findByIdAndDelete(currentUser, postId);
    }










}
