package map.homepage.domain.post;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import map.homepage.domain.post.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public List<PostResponseDTO> getPostList() {
        return postService.getPostList();
    }

}

