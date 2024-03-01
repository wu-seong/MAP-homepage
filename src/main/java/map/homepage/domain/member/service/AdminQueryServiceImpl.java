package map.homepage.domain.member.service;

import lombok.RequiredArgsConstructor;
import map.homepage.domain.feedback.Feedback;
import map.homepage.domain.feedback.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminQueryServiceImpl implements AdminQueryService{
    private final FeedbackRepository feedbackRepository;
    @Override
    public List<Feedback> getFeedbackList() {
        return feedbackRepository.findAll();
    }
}
