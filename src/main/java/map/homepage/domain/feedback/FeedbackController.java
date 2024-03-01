package map.homepage.domain.feedback;


import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.feedback.dto.FeedbackRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackServiceImpl feedbackService;
    @PostMapping("")
    public ApiResponse<String> createFeedback(@RequestBody FeedbackRequestDTO requestDTO){
        feedbackService.create(requestDTO.getContent());
        return ApiResponse.onSuccess("피드백 작성 성공!");
    }

}
