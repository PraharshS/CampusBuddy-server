package net.project.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.project.springboot.models.TimeTable;
import net.project.springboot.repository.TimeTableRepository;

public class TimeTableService {
    @Autowired
    private TimeTableRepository timeTableRepository;

    public List<TimeTable> getTimeTable() {
        return timeTableRepository.findAll();
    }
}
