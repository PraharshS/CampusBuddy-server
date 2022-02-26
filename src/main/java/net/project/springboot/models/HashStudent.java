package net.project.springboot.models;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students_password_hash")
public class HashStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    private SecretKeySpec hashKeySpec;
    private String hashedPassword;

    public SecretKeySpec getHashKeySpec() {
        return hashKeySpec;
    }

    @Override
    public String toString() {
        return "HashStudent [hashKeySpec=" + hashKeySpec + ", hashedPassword=" + hashedPassword + ", id=" + id;
    }

    public void setHashKeySpec(SecretKeySpec hashKeySpec) {
        this.hashKeySpec = hashKeySpec;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

}
