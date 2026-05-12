package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.request.TripDTORequest;
import com.tairitsu.driverincome.dto.response.TripDTOResponse;
import com.tairitsu.driverincome.entity.Trip;
import com.tairitsu.driverincome.exception.custom.ResourceNotFoundException;
import com.tairitsu.driverincome.repository.TripRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceImplementTest {
    @Mock
    private TripRepository tripRepository;
    @InjectMocks
    private TripServiceImplement tripService;
    private Trip trip;
    private TripDTORequest request;
    @BeforeEach
    void setUp() {
        trip = new Trip();
        trip.setId(1L);
        trip.setNetIncome(BigDecimal.valueOf(100));
        trip.setTip(BigDecimal.valueOf(20));
        trip.setTotal(BigDecimal.valueOf(120));
        request = new TripDTORequest();
        request.setNetIncome(BigDecimal.valueOf(100));
        request.setTip(BigDecimal.valueOf(20));
    }
    @Test
    void createTrip_HappyCase() {
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        TripDTOResponse response = tripService.createTrip(request);
        assertNotNull(response);
        verify(tripRepository).save(any(Trip.class));
    }
    @Test
    void createTrip_NullTip_ShouldDefaultToZero() {
        request.setTip(null);
        Trip savedTrip = new Trip();
        savedTrip.setId(1L);
        savedTrip.setNetIncome(BigDecimal.valueOf(100));
        savedTrip.setTip(BigDecimal.ZERO);
        savedTrip.setTotal(BigDecimal.valueOf(100));
        when(tripRepository.save(any(Trip.class))).thenReturn(savedTrip);
        TripDTOResponse response = tripService.createTrip(request);
        assertNotNull(response);
        verify(tripRepository).save(any(Trip.class));
    }
    @Test
    void deleteTrip_HappyCase() {
        when(tripRepository.existsById(1L)).thenReturn(true);
        tripService.deleteTrip(1L);
        verify(tripRepository).deleteById(1L);
    }
    @Test
    void deleteTrip_UnhappyCase_TripNotFound() {
        when(tripRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,
                () -> tripService.deleteTrip(1L));
        verify(tripRepository, never()).deleteById(anyLong());
    }
    @Test
    void getTrip_HappyCase() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        TripDTOResponse response = tripService.getTrip(1L);
        assertNotNull(response);
    }
    @Test
    void getTrip_UnhappyCase_NotFound() {
        when(tripRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> tripService.getTrip(1L));
    }
    @Test
    void getAllTrip_HappyCase() {
        when(tripRepository.findAll()).thenReturn(List.of(trip));
        List<TripDTOResponse> responses = tripService.getAllTrip();
        assertEquals(1, responses.size());
    }
    @Test
    void getAllTrip_EmptyList() {
        when(tripRepository.findAll()).thenReturn(List.of());
        List<TripDTOResponse> responses = tripService.getAllTrip();
        assertTrue(responses.isEmpty());
    }
    @Test
    void updateTrip_HappyCase() {
        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        TripDTOResponse response = tripService.updateTrip(1L, request);
        assertNotNull(response);
        verify(tripRepository).save(any(Trip.class));
    }
    @Test
    void updateTrip_UnhappyCase_NotFound() {
        when(tripRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> tripService.updateTrip(1L, request));
    }
    @Test
    void getTrips_HappyCase() {
        Page<Trip> page = new PageImpl<>(List.of(trip));
        when(tripRepository.findAll(PageRequest.of(0, 5)))
                .thenReturn(page);
        Page<TripDTOResponse> responses = tripService.getTrips(0, 5);
        assertEquals(1, responses.getTotalElements());
    }
    @Test
    void getTrips_EmptyPage() {
        Page<Trip> page = new PageImpl<>(List.of());
        when(tripRepository.findAll(PageRequest.of(0, 5))).thenReturn(page);
        Page<TripDTOResponse> responses = tripService.getTrips(0, 5);
        assertTrue(responses.isEmpty());
    }
}