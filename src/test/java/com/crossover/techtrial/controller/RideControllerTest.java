package com.crossover.techtrial.controller;


import com.crossover.techtrial.dto.TopDriverDTO;
import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Abun on 12/7/2018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RideControllerTest {

    MockMvc mockMvc;

    @Mock
    private RideController rideController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    RideRepository rideRepository;

    @LocalServerPort
    private int port;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
    }

    @Test
    public void createNewRide() throws Exception {
        HttpEntity<Object> ride = getHttpEntity(
                "{\"start_time\": \"5:30 pm\", \"end_time\": \"6:30 pm\","
                        + " \"distance\": \"200\", \"driver_id\": \"1\", \"rider_id\": \"1\" }");
        ResponseEntity<Ride> response = template.postForEntity(
                "/api/ride", ride, Ride.class);
        //Delete this user
        rideRepository.deleteById(response.getBody().getId());
        Assert.assertEquals("5:30 pm", response.getBody().getStartTime());
        Assert.assertEquals(200,response.getStatusCode().value());
    }

    @Test
    public void getRideById() throws Exception {
        Ride ride = new Ride();
        ride.setStartTime("5:30 pm");
        ride.setEndTime("6:30 pm");
        ride.setDistance((long)45);
        ride.setDriver(new Person());
        ride.setRider(new Person());
        ride.setId((long)2);

        String inputInJson = this.mapToJson(ride);

        String URIToCreateRide = "/api/ride";
        HttpEntity<Ride> entity = new HttpEntity<Ride>(ride, headers);
        template.exchange(formFullURLWithPort(URIToCreateRide),
                HttpMethod.POST, entity, String.class);

        String URI = "/api/ride/2";

        String bodyJsonResponse = template.getForObject(formFullURLWithPort(URI), String.class);
        Assert.assertEquals(bodyJsonResponse,inputInJson);
    }

    @Test
    public void getTopDriver() throws Exception {
        TopDriverDTO topDriverDTO1 = new TopDriverDTO();
        topDriverDTO1.setName("peter");
        topDriverDTO1.setEmail("peter@pit.com");
        topDriverDTO1.setAverageDistance((double) 35);
        topDriverDTO1.setMaxRideDurationInSecods((long) 345);
        topDriverDTO1.setTotalRideDurationInSeconds((long) 345);

        TopDriverDTO topDriverDTO2 = new TopDriverDTO();
        topDriverDTO2.setName("john");
        topDriverDTO2.setEmail("john@jonny.com");
        topDriverDTO2.setAverageDistance((double) 35);
        topDriverDTO2.setMaxRideDurationInSecods((long) 345);
        topDriverDTO2.setTotalRideDurationInSeconds((long) 345);

        TopDriverDTO topDriverDTO3 = new TopDriverDTO();
        topDriverDTO3.setName("andrew");
        topDriverDTO3.setEmail("andrew@andy.com");
        topDriverDTO3.setAverageDistance((double) 35);
        topDriverDTO3.setMaxRideDurationInSecods((long) 345);
        topDriverDTO3.setTotalRideDurationInSeconds((long) 345);

        TopDriverDTO topDriverDTO4 = new TopDriverDTO();
        topDriverDTO4.setName("james");
        topDriverDTO4.setEmail("james@jammy.com");
        topDriverDTO4.setAverageDistance((double) 35);
        topDriverDTO4.setMaxRideDurationInSecods((long) 345);
        topDriverDTO4.setTotalRideDurationInSeconds((long) 345);

        TopDriverDTO topDriverDTO5 = new TopDriverDTO();
        topDriverDTO5.setName("luke");
        topDriverDTO5.setEmail("luke@luky.com");
        topDriverDTO5.setAverageDistance((double) 35);
        topDriverDTO5.setMaxRideDurationInSecods((long) 345);
        topDriverDTO5.setTotalRideDurationInSeconds((long) 345);

        List<TopDriverDTO> topDriverDTOList = new ArrayList<>();
        topDriverDTOList.add(topDriverDTO1);
        topDriverDTOList.add(topDriverDTO2);
        topDriverDTOList.add(topDriverDTO3);
        topDriverDTOList.add(topDriverDTO4);
        topDriverDTOList.add(topDriverDTO5);

        String inputInJson = this.mapToJson(topDriverDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/top-rides"))
                .andExpect(MockMvcResultMatchers.content().string(inputInJson));

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