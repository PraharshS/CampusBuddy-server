package net.project.springboot.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.project.springboot.models.Admin;
import net.project.springboot.models.Feedback;
import net.project.springboot.models.Notice;
import net.project.springboot.models.Student;
import net.project.springboot.service.AdminService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // CREATE ADMIN
    @PostMapping("/admins")
    public Admin createAdmin(@RequestBody Admin admin)
            throws GeneralSecurityException, IOException {
        return adminService.createAdmin(admin);
    }

    // login Admin
    @PostMapping("/admins/login")
    public Admin loginAdmin(@RequestBody Admin admin) throws GeneralSecurityException, IOException {
        return adminService.loginAdmin(admin);
    }

    // get all students
    @GetMapping("/admins/allStudents")
    public List<Student> getAllStudents() {
        return adminService.getAllStudents();
    }

    @GetMapping("/admin/notices")
    public List<Notice> getAllNotices() {
        return adminService.getAllNotices();
    }

    @PostMapping("/admin/notice")
    public void addNotice(@RequestBody Notice notice) {
        adminService.addNotice(notice);
    }

    @GetMapping("/admin/feedbacks")
    public List<Feedback> getAllFeedbacks() {
        return adminService.getAllFeedbacks();
    }
}
