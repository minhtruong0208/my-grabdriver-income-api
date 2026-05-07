package com.tairitsu.driverincome.service;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TripService {
    TripDTOResponse createTrip(TripDTORequest req);
    void deleteTrip(Long id);
    TripDTOResponse getTrip(Long id);
    List<TripDTOResponse> getAllTrip();
    TripDTOResponse updateTrip(Long id, TripDTORequest req);
    Page<TripDTOResponse> getTrips(int page, int size);
}
