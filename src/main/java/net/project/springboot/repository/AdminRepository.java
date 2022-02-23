package net.project.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.project.springboot.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByEmailAndPassword(String email, String password);

    List<Admin> findByEmail(String email);

}
