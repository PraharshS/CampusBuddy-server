package net.project.springboot.service;

import static net.project.springboot.encryption.Encryption.createSecretKey;
import static net.project.springboot.encryption.Encryption.decrypt;
import static net.project.springboot.encryption.Encryption.encrypt;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.project.springboot.encryption.GenerateEncryptionKey;
import net.project.springboot.models.Admin;
import net.project.springboot.models.Feedback;
import net.project.springboot.models.Notice;
import net.project.springboot.models.Student;
import net.project.springboot.repository.AdminRepository;
import net.project.springboot.repository.FeedbackRepository;
import net.project.springboot.repository.NoticeRepository;
import net.project.springboot.repository.StudentRepository;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Admin createAdmin(Admin admin)
            throws GeneralSecurityException, IOException {
        String password = admin.getPassword();

        SecretKeySpec hashKeySpec = createSecretKey(password.toCharArray(), GenerateEncryptionKey.salt,
                GenerateEncryptionKey.iterationCount, GenerateEncryptionKey.keyLength);
        admin.setHashKeySpec(hashKeySpec);

        String encryptedPass = encrypt(password, hashKeySpec);
        admin.setPassword(encryptedPass);

        return adminRepository.save(admin);
    }

    public Admin loginAdmin(Admin loginAdmin)
            throws GeneralSecurityException, IOException {
        // Find the Admin by email
        List<Admin> adminList = adminRepository.findByEmail(loginAdmin.getEmail());
        if (adminList.isEmpty()) {
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

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    public void addNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }
}
