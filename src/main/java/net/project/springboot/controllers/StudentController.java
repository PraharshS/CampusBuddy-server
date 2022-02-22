package net.project.springboot.controllers;

import static net.project.springboot.encryption.Encryption.createSecretKey;
import static net.project.springboot.encryption.Encryption.decrypt;
import static net.project.springboot.encryption.Encryption.encrypt;

import java.io.IOException;
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
		byte[] salt = new String("12345678").getBytes();
		int iterationCount = 40000;
		int keyLength = 128;
		SecretKeySpec key = createSecretKey(password.toCharArray(), salt, iterationCount, keyLength);
		String ogPassword = password;

		String encryptedPass = encrypt(ogPassword, key);
		student.setPassword(encryptedPass);

		String decryptedPass = decrypt(encryptedPass, key);

		return studentRepository.save(student);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student Does Not Exist with id : " + id));

		student.setName(updatedStudent.getName());
		student.setEmail(updatedStudent.getEmail());
		student.setPassword(updatedStudent.getPassword());
		student.setEnNumber(updatedStudent.getEnNumber());
		student.setBranch(updatedStudent.getBranch());
		student.setSemester(updatedStudent.getSemester());
		student.setContactNumber(updatedStudent.getContactNumber());

		studentRepository.save(student);
		return ResponseEntity.ok(student);
	}

	// login student
	@PostMapping("/students/login")
	public Student loginStudent(@RequestBody Student student) {
		List<Student> studList = studentRepository.findByEmailAndPassword(student.getEmail(), student.getPassword());
		if (studList.size() == 0) {
			return new Student();
		}
		return studList.get(0);
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
