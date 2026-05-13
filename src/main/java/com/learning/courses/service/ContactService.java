package com.learning.courses.service;

import com.learning.courses.dto.ContactDTO;
import com.learning.courses.dto.CreateContactDTO;
import com.learning.courses.exception.EntityNotFoundException;
import com.learning.courses.exception.InvalidRoleException;
import com.learning.courses.mapper.ContactMapper;
import com.learning.courses.model.Contact;
import com.learning.courses.model.enums.Role;
import com.learning.courses.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final PersonService personService;

    @Transactional
    public Long createContact(CreateContactDTO createContactDTO) {
        var student = personService.getPersonEntity(createContactDTO.getStudentId());

        if (student.getRole() != Role.STUDENT) {
            throw new InvalidRoleException(createContactDTO.getStudentId(), Role.STUDENT, student.getRole());
        }

        var contact = contactMapper.toEntity(createContactDTO);
        contact.setStudent(student);

        return contactRepository.save(contact).getId();
    }

    @Transactional(readOnly = true)
    public ContactDTO getContact(Long id) {
        return contactRepository.findById(id)
                .map(contactMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(id, Contact.class.getSimpleName()));
    }

    @Transactional(readOnly = true)
    public List<ContactDTO> getStudentContacts(Long studentId) {
        return contactMapper.toDTO(contactRepository.findAllByStudentId(studentId));
    }

    @Transactional
    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new EntityNotFoundException(id, Contact.class.getSimpleName());
        }
        contactRepository.deleteById(id);
    }
}
