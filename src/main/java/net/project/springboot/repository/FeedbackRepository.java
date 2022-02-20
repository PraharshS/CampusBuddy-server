package net.project.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.project.springboot.models.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
