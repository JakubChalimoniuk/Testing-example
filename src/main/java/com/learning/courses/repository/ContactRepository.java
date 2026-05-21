package com.learning.courses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.courses.model.Contact;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByStudentId(Long studentId);
}
