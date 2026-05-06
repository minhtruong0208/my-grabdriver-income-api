package com.tairitsu.driverincome.service;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;

public interface TripService {
    public TripDTOResponse createTrip(TripDTORequest req);
    void deleteTrip(Long id);
}
