package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.repositories.PersonRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abun on 12/7/2018.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceImplTest {

    @MockBean
    PersonRepository personRepository;

    @Autowired
    PersonService personService;

    @Test
    public void getAll() throws Exception {
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
        assert(personService.getAll()).equals(personList);
    }

    @Test
    public void save() throws Exception {
        Person person1 = new Person();
        person1.setId((long)1);
        person1.setName("Peter");
        person1.setEmail("peter@peterson.com");
        person1.setRegistrationNumber("1234567");

        Mockito.when(personRepository.save(person1)).thenReturn(person1);
        assert(personService.save(person1)).equals(person1);
    }

    @Test
    public void findById() throws Exception {
        Person person1 = new Person();
        person1.setId((long)1);
        person1.setName("Peter");
        person1.setEmail("peter@peterson.com");
        person1.setRegistrationNumber("1234567");

        Mockito.when(personRepository.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(person1));
        assert(personService.findById((long) 1)).equals(person1);
    }

}