package net.project.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.project.springboot.models.Notice;
import net.project.springboot.repository.NoticeRepository;

public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    public List<Notice> getNoticeList() {
        return noticeRepository.findAll();
    }
}
