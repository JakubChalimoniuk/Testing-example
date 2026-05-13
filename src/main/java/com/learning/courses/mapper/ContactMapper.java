package com.learning.courses.mapper;

import com.learning.courses.dto.ContactDTO;
import com.learning.courses.dto.CreateContactDTO;
import com.learning.courses.model.Contact;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ContactMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student.id", source = "studentId")
    Contact toEntity(CreateContactDTO createContactDTO);

    @Mapping(target = "studentId", source = "student.id")
    ContactDTO toDTO(Contact contact);

    List<ContactDTO> toDTO(List<Contact> contactList);
}