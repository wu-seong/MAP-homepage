package map.homepage.domain.member.service;

import map.homepage.domain.feedback.Feedback;

import java.util.List;

public interface AdminQueryService {
    public List<Feedback> getFeedbackList();
}
