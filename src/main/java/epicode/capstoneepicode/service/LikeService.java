package epicode.capstoneepicode.service;

import epicode.capstoneepicode.entities.post.Like;
import epicode.capstoneepicode.entities.post.Post;
import epicode.capstoneepicode.exceptions.NotFoundException;
import epicode.capstoneepicode.repository.LikeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeDAO likeDAO;

    public Like findByPost(Post post) {
        return likeDAO.findByPost(post).orElseThrow(()-> new NotFoundException(post.getId()));
    }
}
