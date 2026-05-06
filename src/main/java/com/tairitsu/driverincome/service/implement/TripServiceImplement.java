package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;
import com.tairitsu.driverincome.entity.Trip;
import com.tairitsu.driverincome.exception.custom.ResourceNotFoundException;
import com.tairitsu.driverincome.mapper.TripMapper;
import com.tairitsu.driverincome.repository.TripRepository;
import com.tairitsu.driverincome.service.TripService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TripServiceImplement implements TripService {
    private final TripRepository tripRepository;
    public TripServiceImplement(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
    @Override
    public TripDTOResponse createTrip(TripDTORequest req) {
        Trip trip = TripMapper.mapToTrip(req);
        BigDecimal tip = Optional.ofNullable(trip.getTip())
                .orElse(BigDecimal.ZERO);
        trip.setTip(tip);
        trip.setTotal(trip.getNetIncome().add(tip));
        Trip saved = tripRepository.save(trip);
        return TripMapper.mapToTripDTOResponse(saved);
    }
    @Override
    public void deleteTrip(Long id) {
        if (!tripRepository.existsById(id)) {
            throw new ResourceNotFoundException("Trip not found with id = " + id);
        }
        tripRepository.deleteById(id);
    }
}
