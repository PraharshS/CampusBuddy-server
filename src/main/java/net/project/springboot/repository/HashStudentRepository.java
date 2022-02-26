package net.project.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.project.springboot.models.HashStudent;

@Repository
public interface HashStudentRepository extends JpaRepository<HashStudent, Long> {
    List<HashStudent> findByStudentId(Long studentId);
}
