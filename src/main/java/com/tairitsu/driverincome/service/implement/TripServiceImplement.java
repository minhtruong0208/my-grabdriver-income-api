package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;
import com.tairitsu.driverincome.entity.Trip;
import com.tairitsu.driverincome.mapper.TripMapper;
import com.tairitsu.driverincome.repository.TripRepository;
import com.tairitsu.driverincome.service.TripService;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImplement implements TripService {
    private final TripRepository tripRepository;
    public TripServiceImplement(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
    @Override
    public TripDTOResponse createTrip(TripDTORequest req) {
        Trip trip = TripMapper.mapToTrip(req);
        trip.setTotal(
                trip.getNetIncome().add(trip.getTip())
        );
        Trip saved = tripRepository.save(trip);
        return TripMapper.mapToTripDTOResponse(saved);
    }
}
