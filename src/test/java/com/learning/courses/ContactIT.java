package com.learning.courses;

import com.fasterxml.jackson.core.type.TypeReference;
import com.learning.courses.dto.ContactDTO;
import com.learning.courses.dto.CreateContactDTO;
import com.learning.courses.repository.ContactRepository;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactIT extends AbstractIntegrationTest {

    private static final Long TUTOR_ID = 77L;
    private static final Long STUDENT_ID = 88L;
    private static final Long EXISTING_CONTACT_ID = 99L;

    @Autowired
    private ContactRepository contactRepository;

    @Test
    @Sql("/sql/CreateContactInit.sql")
    void shouldSuccessfullyCreateNewContactForStudent() throws Exception {
        // given
        final HttpPost httpPost = new HttpPost("/api/contacts");
        CreateContactDTO contactDTO = CreateContactDTO.builder()
                .name("Służbowy")
                .email("nowy.student@example.com")
                .address("ul. Testowa 2, Warszawa")
                .phoneNumber("987-654-321")
                .studentId(STUDENT_ID)
                .build();

        initRequestWithBody(contactDTO, httpPost);

        // when
        try (var client = HttpClients.createDefault()) {
            var response = client.execute(getHttpHost(), httpPost);
            var contactId = retrieveResourceFromResponse(response, Long.class);

            // then
            assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.OK.value());

            var contactOpt = contactRepository.findById(contactId);
            assertThat(contactOpt).isPresent();
            assertThat(contactOpt.get().getEmail()).isEqualTo("nowy.student@example.com");
            assertThat(contactOpt.get().getStudent().getId()).isEqualTo(STUDENT_ID);
        }
    }

    @Test
    @Sql("/sql/CreateContactInit.sql")
    void shouldFailToCreateContactIfAssignedPersonIsNotStudent() throws Exception {
        // given
        final HttpPost httpPost = new HttpPost("/api/contacts");
        CreateContactDTO contactDTO = CreateContactDTO.builder()
                .name("Prywatny")
                .email("tutor.contact@example.com")
                .address("ul. Nauczycielska 5")
                .phoneNumber("111-222-333")
                .studentId(TUTOR_ID)
                .build();
        initRequestWithBody(contactDTO, httpPost);

        // when
        try (var client = HttpClients.createDefault()) {
            var response = client.execute(getHttpHost(), httpPost);

            // then
            assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Test
    @Sql("/sql/CreateContactInit.sql")
    void shouldReturnAllContactsForGivenStudentId() throws Exception {
        // given
        final HttpGet httpGet = new HttpGet("/api/contacts/student/" + STUDENT_ID);

        // when
        try (var client = HttpClients.createDefault()) {
            var response = client.execute(getHttpHost(), httpGet);

            final String jsonFromResponse = EntityUtils.toString(response.getEntity());
            List<ContactDTO> contacts = objectMapper.readValue(jsonFromResponse, new TypeReference<List<ContactDTO>>() {});

            // then
            assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(contacts).hasSize(1);
            assertThat(contacts.get(0).getId()).isEqualTo(EXISTING_CONTACT_ID);
            assertThat(contacts.get(0).getEmail()).isEqualTo("jakub.chalimoniuk@gmail.com");
        }
    }

    @Test
    @Sql("/sql/CreateContactInit.sql")
    void shouldDeleteContactSuccessfully() throws Exception {
        // given
        final HttpDelete httpDelete = new HttpDelete("/api/contacts/" + EXISTING_CONTACT_ID);

        assertThat(contactRepository.existsById(EXISTING_CONTACT_ID)).isTrue();

        // when
        try (var client = HttpClients.createDefault()) {
            var response = client.execute(getHttpHost(), httpDelete);

            // then
            assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(contactRepository.existsById(EXISTING_CONTACT_ID)).isFalse();
        }
    }
    @Test
    @Sql("/sql/CreateContactInit.sql")
    void shouldDeleteContactsWhenPersonIsDeleted() throws Exception {

        // TODO: FIX THIS UGLY CODE
//        // given
//        final HttpDelete httpDelete = new HttpDelete("/api/persons/" + STUDENT_ID);
//
//        assertThat(contactRepository.existsById(EXISTING_CONTACT_ID)).isTrue();
//
//        // when
//        try (var client = HttpClients.createDefault()) {
//            var response = client.execute(getHttpHost(), httpDelete);
//
//            // then
//            assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.OK.value());
//
//            assertThat(contactRepository.existsById(EXISTING_CONTACT_ID)).isFalse();
        }
    }
}