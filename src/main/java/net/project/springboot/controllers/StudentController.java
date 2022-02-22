package net.project.springboot.controllers;

import java.util.List;
import java.util.Optional;

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
	public Student createStudent(@RequestBody Student student) {
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
