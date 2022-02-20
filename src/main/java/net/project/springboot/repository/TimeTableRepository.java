package net.project.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.project.springboot.models.TimeTable;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

}
