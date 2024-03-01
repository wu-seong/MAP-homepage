package map.homepage.domain.feedback;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl {
    private final FeedbackRepository feedbackRepository;

    public Feedback create(String content){
        Feedback newFeedback = Feedback.builder()
                .content(content)
                .build();
        return feedbackRepository.save(newFeedback);
    }
}
