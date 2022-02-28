package net.project.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.project.springboot.models.TimeTable;
import net.project.springboot.repository.TimeTableRepository;

@Service
public class TimeTableService {
    @Autowired
    private TimeTableRepository timeTableRepository;

    public List<TimeTable> getTimeTable() {
        return timeTableRepository.findAll();
    }
}
