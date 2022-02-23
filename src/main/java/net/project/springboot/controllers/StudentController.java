package net.project.springboot.controllers;

import static net.project.springboot.encryption.Encryption.createSecretKey;
import static net.project.springboot.encryption.Encryption.encrypt;
import static net.project.springboot.encryption.Encryption.decrypt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.project.springboot.encryption.GenerateEncryptionKey;
import net.project.springboot.exception.ResourceNotFoundException;
import net.project.springboot.models.Feedback;
import net.project.springboot.models.Student;
import net.project.springboot.models.TimeTable;
import net.project.springboot.repository.FeedbackRepository;
import net.project.springboot.repository.StudentRepository;
import net.project.springboot.repository.TimeTableRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private TimeTableRepository timeTableRepository;
	@Autowired
	private FeedbackRepository feedbackRepository;

	// get all students
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	// create student rest api

	@PostMapping("/students")
	public Student createStudent(@RequestBody Student student)
			throws GeneralSecurityException, IOException {
		String password = student.getPassword();

		SecretKeySpec hashKeySpec = createSecretKey(password.toCharArray(), GenerateEncryptionKey.salt,
				GenerateEncryptionKey.iterationCount, GenerateEncryptionKey.keyLength);
		student.setHashKeySpec(hashKeySpec);

		String encryptedPass = encrypt(password, hashKeySpec);
		student.setPassword(encryptedPass);

		return studentRepository.save(student);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent)
			throws UnsupportedEncodingException, GeneralSecurityException {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student Does Not Exist with id : " + id));

		student.setName(updatedStudent.getName());
		student.setEmail(updatedStudent.getEmail());

		SecretKeySpec updatedHashKeySpec = createSecretKey(updatedStudent.getPassword().toCharArray(),
				GenerateEncryptionKey.salt,
				GenerateEncryptionKey.iterationCount, GenerateEncryptionKey.keyLength);
		student.setHashKeySpec(updatedHashKeySpec);
		String encryptedPass = encrypt(updatedStudent.getPassword(), updatedHashKeySpec);
		student.setPassword(encryptedPass);

		student.setEnNumber(updatedStudent.getEnNumber());
		student.setBranch(updatedStudent.getBranch());
		student.setSemester(updatedStudent.getSemester());
		student.setContactNumber(updatedStudent.getContactNumber());

		studentRepository.save(student);
		return ResponseEntity.ok(student);
	}

	// login student
	@PostMapping("/students/login")
	public Student loginStudent(@RequestBody Student loginStudent)
			throws GeneralSecurityException, IOException {
		// Find the student by email
		List<Student> studentList = studentRepository.findByEmail(loginStudent.getEmail());
		if (studentList.size() == 0) {
			return new Student();
		}
		// if found then , decrypt the encrypted password stored in db
		Student student = studentList.get(0);
		// hashKeySpec is different for every password
		String decryptedPass = decrypt(student.getPassword(), student.getHashKeySpec());
		student.setPassword(decryptedPass);
		if (loginStudent.getPassword().equals(decryptedPass)) {
			return student;
		}
		return new Student();
	}

	@GetMapping("/student/time-table")
	public List<TimeTable> getSchedule() {
		return timeTableRepository.findAll();
	}

	@PostMapping("/student/feedback")
	public void createFeedback(@RequestBody Feedback feedback) {
		feedbackRepository.save(feedback);
	}
}
