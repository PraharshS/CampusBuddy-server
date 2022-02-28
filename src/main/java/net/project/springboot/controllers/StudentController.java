package net.project.springboot.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.project.springboot.models.Feedback;
import net.project.springboot.models.Notice;
import net.project.springboot.models.Student;
import net.project.springboot.models.TimeTable;
import net.project.springboot.service.FeedbackService;
import net.project.springboot.service.NoticeService;
import net.project.springboot.service.StudentService;
import net.project.springboot.service.TimeTableService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private TimeTableService timeTableService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private FeedbackService feedbackService;

	// get all students
	@GetMapping("/students")
	public List<Student> getStudents() {
		return studentService.getStudents();
	}

	// create student rest api

	@PostMapping("/students")
	public Student addStudent(@RequestBody Student student)
			throws UnsupportedEncodingException, GeneralSecurityException {
		return studentService.addStudent(student);
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
		return studentService.updateStudent(id, updatedStudent);
	}// delete student rest api

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteStudent(@PathVariable Long id) {
		return studentService.deleteStudent(id);
	}

	// login student
	@PostMapping("/students/login")
	public Student loginStudent(@RequestBody Student loginStudent)
			throws GeneralSecurityException, IOException {
		return studentService.loginStudent(loginStudent);
	}

	@GetMapping("/student/time-table")
	public List<TimeTable> getTimeTable() {
		return timeTableService.getTimeTable();
	}

	@GetMapping("/student/notices")
	public List<Notice> getNoticeList() {
		return noticeService.getNoticeList();
	}

	@PostMapping("/student/feedback")
	public void addFeedback(@RequestBody Feedback feedback) {
		feedbackService.addFeedback(feedback);
	}
}
