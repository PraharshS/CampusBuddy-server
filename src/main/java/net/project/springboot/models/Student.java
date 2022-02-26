package net.project.springboot.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "students")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;
	private String email;
	private String password;

	@Column(name = "enrollment_number")
	private String enNumber;
	private String branch;
	private String semester;

	@Column(name = "contact_number")
	private String contactNumber;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
	private HashStudent hStudent;

	public void sethStudent(HashStudent hStudent) {
		this.hStudent = hStudent;
	}

	public HashStudent gethStudent() {
		return hStudent;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnNumber() {
		return enNumber;
	}

	@Override
	public String toString() {
		return "Student [branch=" + branch + ", contactNumber=" + contactNumber + ", email=" + email + ", enNumber="
				+ enNumber + ", hStudent=" + hStudent + ", id=" + id + ", name=" + name + ", password=" + password
				+ ", semester=" + semester + "]";
	}

	public void setEnNumber(String enNumber) {
		this.enNumber = enNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
