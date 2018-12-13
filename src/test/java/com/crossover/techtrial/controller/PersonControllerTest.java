/**
 * 
 */
package com.crossover.techtrial.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author kshah
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

  MockMvc mockMvc;
  
  @Mock
  private PersonController personController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  PersonRepository personRepository;

  @LocalServerPort
  private int port;

  private HttpHeaders headers = new HttpHeaders();

  @Before
  public void setup() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
  }

  @Test
  public void testPanelShouldBeRegistered() throws Exception {
    HttpEntity<Object> person = getHttpEntity(
        "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\"," 
            + " \"registrationNumber\": \"41DCT\" }");
    ResponseEntity<Person> response = template.postForEntity(
        "/api/person", person, Person.class);
    //Delete this user
    personRepository.deleteById(response.getBody().getId());
    Assert.assertEquals("test 1", response.getBody().getName());
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void getAllPersons() throws Exception {
    Person person1 = new Person();
    person1.setId((long)1);
    person1.setName("Peter");
    person1.setEmail("peter@peterson.com");
    person1.setRegistrationNumber("1234567");

    Person person2 = new Person();
    person2.setId((long)2);
    person2.setName("John");
    person2.setEmail("john@jonny.com");
    person2.setRegistrationNumber("1234567");

    List<Person> personList = new ArrayList<>();
    personList.add(person1);
    personList.add(person2);

    Mockito.when(personRepository.findAll()).thenReturn(personList);
    assert(personRepository.findAll()).equals(personList);
  }

  @Test
  public void getPersonById() throws Exception {
    Person person = new Person();
    person.setId((long)1);
    person.setName("Peter");
    person.setEmail("peter@peterson.com");
    person.setRegistrationNumber("1234567");

    String inputInJson = this.mapToJson(person);

    String URIToRegisterPerson = "/api/person";
    HttpEntity<Person> entity = new HttpEntity<Person>(person, headers);
    template.exchange(formFullURLWithPort(URIToRegisterPerson),
            HttpMethod.POST, entity, String.class);

    String URI = "/api/person/1";

    String bodyJsonResponse = template.getForObject(formFullURLWithPort(URI), String.class);
    assert(bodyJsonResponse).equalsIgnoreCase(inputInJson);

  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

  /**
   * this utility method Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
   */
  private String mapToJson(Object object) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(object);
  }

  /**
   * This utility method to create the url for given uri. It appends the RANDOM_PORT generated port
   */
  private String formFullURLWithPort(String uri) {
    return "http://localhost:" + port + uri;
  }

}
