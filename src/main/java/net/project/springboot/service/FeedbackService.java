package net.project.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;

import net.project.springboot.models.Feedback;
import net.project.springboot.repository.FeedbackRepository;

public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public void addFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }
}
