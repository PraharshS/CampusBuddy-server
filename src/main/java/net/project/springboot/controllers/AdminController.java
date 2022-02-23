package net.project.springboot.controllers;

import static net.project.springboot.encryption.Encryption.createSecretKey;
import static net.project.springboot.encryption.Encryption.encrypt;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.project.springboot.encryption.GenerateEncryptionKey;
import net.project.springboot.models.Admin;
import net.project.springboot.repository.AdminRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

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
}
