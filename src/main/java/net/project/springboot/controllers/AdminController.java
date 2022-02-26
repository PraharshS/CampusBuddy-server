package net.project.springboot.controllers;

import static net.project.springboot.encryption.Encryption.createSecretKey;
import static net.project.springboot.encryption.Encryption.decrypt;
import static net.project.springboot.encryption.Encryption.encrypt;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.project.springboot.encryption.GenerateEncryptionKey;
import net.project.springboot.models.Admin;
import net.project.springboot.models.Notice;
import net.project.springboot.models.Student;
import net.project.springboot.repository.AdminRepository;
import net.project.springboot.repository.NoticeRepository;
import net.project.springboot.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private NoticeRepository noticeRepository;

    // CREATE ADMIN
    @PostMapping("/admins")
    public Admin createAdmin(@RequestBody Admin admin)
            throws GeneralSecurityException, IOException {
        String password = admin.getPassword();

        SecretKeySpec hashKeySpec = createSecretKey(password.toCharArray(), GenerateEncryptionKey.salt,
                GenerateEncryptionKey.iterationCount, GenerateEncryptionKey.keyLength);
        admin.setHashKeySpec(hashKeySpec);

        String encryptedPass = encrypt(password, hashKeySpec);
        admin.setPassword(encryptedPass);

        return adminRepository.save(admin);
    }

    // login Admin
    @PostMapping("/admins/login")
    public Admin loginAdmin(@RequestBody Admin loginAdmin)
            throws GeneralSecurityException, IOException {
        // Find the Admin by email
        List<Admin> adminList = adminRepository.findByEmail(loginAdmin.getEmail());
        if (adminList.size() == 0) {
            return new Admin();
        }
        // if found then , decrypt the encrypted password stored in db
        Admin admin = adminList.get(0);
        // hashKeySpec is different for every password
        String decryptedPass = decrypt(admin.getPassword(), admin.getHashKeySpec());
        admin.setPassword(decryptedPass);
        if (loginAdmin.getPassword().equals(decryptedPass)) {
            return admin;
        }
        return new Admin();
    }

    // get all students
    @GetMapping("/admins/allStudents")
    public List<Student> getAllStudents() throws GeneralSecurityException, IOException {
        List<Student> studentList = studentRepository.findAll();
        for (Student student : studentList) {
            // String encryptedPass = student.getPassword();
            // String decryptedPass = decrypt(encryptedPass, student.getHashKeySpec());
            // student.setPassword(decryptedPass);
        }
        return studentList;
    }

    @GetMapping("/admin/notices")
    public List<Notice> getAllNotices() {
        List<Notice> noticeList = noticeRepository.findAll();
        return noticeList;
    }

    @PostMapping("/admin/notice")
    public void createNotice(@RequestBody Notice notice) {
        noticeRepository.save(notice);
    }

}
