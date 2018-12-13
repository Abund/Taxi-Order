package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Person;
import com.crossover.techtrial.model.Ride;
import com.crossover.techtrial.repositories.RideRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Abun on 12/7/2018.
 */
public class RideServiceImplTest {

    @MockBean
    RideRepository rideRepository;

    @Autowired
    RideService rideService;

    @Test
    public void save() throws Exception {
        Ride ride = new Ride();
        ride.setStartTime("5:30 pm");
        ride.setEndTime("6:30 pm");
        ride.setDistance((long)45);
        ride.setDriver(new Person());
        ride.setRider(new Person());
        ride.setId((long)2);

        Mockito.when(rideRepository.save(ride)).thenReturn(ride);
        assert(rideService.save(ride)).equals(ride);
    }

    @Test
    public void findById() throws Exception {
        Ride ride = new Ride();
        ride.setStartTime("5:30 pm");
        ride.setEndTime("6:30 pm");
        ride.setDistance((long)45);
        ride.setDriver(new Person());
        ride.setRider(new Person());
        ride.setId((long)1);

        Mockito.when(rideRepository.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(ride));
        assert(rideService.findById((long) 1)).equals(ride);
    }

    @Test
    public void getAll() throws Exception {
        Ride ride1 = new Ride();
        ride1.setStartTime("5:30 pm");
        ride1.setEndTime("6:30 pm");
        ride1.setDistance((long)45);
        ride1.setDriver(new Person());
        ride1.setRider(new Person());
        ride1.setId((long)1);

        Ride ride2 = new Ride();
        ride2.setStartTime("5:30 pm");
        ride2.setEndTime("6:30 pm");
        ride2.setDistance((long)45);
        ride2.setDriver(new Person());
        ride2.setRider(new Person());
        ride2.setId((long)1);

        List<Ride> rideList = new ArrayList<>();
        rideList.add(ride1);
        rideList.add(ride2);

        Mockito.when(rideRepository.findAll()).thenReturn(rideList);
        assert(rideService.getAll()).equals(rideList);
    }

}