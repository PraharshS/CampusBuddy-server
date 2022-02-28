package net.project.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.project.springboot.models.Feedback;
import net.project.springboot.repository.FeedbackRepository;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public void addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
}
