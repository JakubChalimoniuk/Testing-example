package com.learning.courses.api.rest.controller;

import com.learning.courses.dto.ContactDTO;
import com.learning.courses.dto.CreateContactDTO;
import com.learning.courses.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "ContactController", description = "Contact API")
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @Operation(summary = "Create contact")
    public Long createContact(@Valid @RequestBody CreateContactDTO createContactDTO) {
        return contactService.createContact(createContactDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get single contact by ID")
    public ContactDTO getContact(@PathVariable Long id) {
        return contactService.getContact(id);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all contacts for a specific student")
    public List<ContactDTO> getStudentContacts(@PathVariable Long studentId) {
        return contactService.getStudentContacts(studentId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete contact")
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
    }
}