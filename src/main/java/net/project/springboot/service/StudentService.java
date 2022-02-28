package net.project.springboot.service;

import static net.project.springboot.encryption.Encryption.createSecretKey;
import static net.project.springboot.encryption.Encryption.decrypt;
import static net.project.springboot.encryption.Encryption.encrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import net.project.springboot.encryption.GenerateEncryptionKey;
import net.project.springboot.exception.ResourceNotFoundException;
import net.project.springboot.models.Student;
import net.project.springboot.repository.StudentRepository;

public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) throws UnsupportedEncodingException, GeneralSecurityException {
        String password = student.getPassword();

        SecretKeySpec hashKeySpec = createSecretKey(password.toCharArray(), GenerateEncryptionKey.salt,
                GenerateEncryptionKey.iterationCount, GenerateEncryptionKey.keyLength);
        String encryptedPass = encrypt(password, hashKeySpec);

        student.setHashKeySpec(hashKeySpec);
        student.setPassword(encryptedPass);

        return studentRepository.save(student);
    }

    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student Does Not Exist with id : " + id));
        student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setEnNumber(updatedStudent.getEnNumber());
        student.setBranch(updatedStudent.getBranch());
        student.setSemester(updatedStudent.getSemester());
        student.setContactNumber(updatedStudent.getContactNumber());

        studentRepository.save(student);
        return ResponseEntity.ok(student);
    }

    public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student Does not exist with id : " + id));

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public Student loginStudent(@RequestBody Student loginStudent) throws GeneralSecurityException, IOException {
        // Find the student by email
        List<Student> studentList = studentRepository.findByEmail(loginStudent.getEmail());
        if (studentList.isEmpty()) {
            return new Student();
        }
        // if found then , decrypt the encrypted password stored in db
        Student student = studentList.get(0);
        String decryptedPass = decrypt(student.getPassword(), student.getHashKeySpec());
        student.setPassword(decryptedPass);
        if (decryptedPass.equals(loginStudent.getPassword())) {
            return student;
        }
        return new Student();

    }

}
