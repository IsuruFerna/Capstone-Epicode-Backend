package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.user.Post;
import epicode.capstoneepicode.exceptions.BadRequestException;
import epicode.capstoneepicode.payload.post.NewPostDTO;
import epicode.capstoneepicode.payload.post.NewPostResponse;
import epicode.capstoneepicode.repository.PostDAO;
import epicode.capstoneepicode.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private PostService postService;

    @GetMapping("")
    public Page<Post> getPosts() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("timeStamp").descending());
        return postDAO.findAll(pageable);
    }

    @PostMapping("")
    public NewPostResponse postPost(@RequestBody @Validated NewPostDTO newPostPayload, BindingResult validation) throws IOException {
        System.out.println(validation);
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("There are Errors in post payload");
        } else {
            Post newPost = postService.save(newPostPayload);
            return new NewPostResponse(newPost.getId());
        }
    }



}
