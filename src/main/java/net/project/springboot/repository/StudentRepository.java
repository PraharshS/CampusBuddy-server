package net.project.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.project.springboot.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
