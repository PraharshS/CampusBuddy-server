package net.project.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.project.springboot.models.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
