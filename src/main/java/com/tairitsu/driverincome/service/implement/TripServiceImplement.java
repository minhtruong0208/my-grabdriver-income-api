package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;
import com.tairitsu.driverincome.entity.Trip;
import com.tairitsu.driverincome.exception.custom.ResourceNotFoundException;
import com.tairitsu.driverincome.mapper.TripMapper;
import com.tairitsu.driverincome.repository.TripRepository;
import com.tairitsu.driverincome.service.TripService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
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
    @Override
    public TripDTOResponse getTrip(Long id) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip not found with id = " + id));
        return TripMapper.mapToTripDTOResponse(trip);
    }
    @Override
    public List<TripDTOResponse> getAllTrip(){
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(TripMapper::mapToTripDTOResponse).toList();
    }
    @Override
    public TripDTOResponse updateTrip(Long id, TripDTORequest req) {
        Trip existedTrip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip not found with id = " + id));
        TripMapper.updateTripFromRequest(req, existedTrip);
        Trip udatedTrip = tripRepository.save(existedTrip);
        return TripMapper.mapToTripDTOResponse(udatedTrip);
    }
    @Override
    public Page<TripDTOResponse> getTrips(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> trips = tripRepository.findAll(pageable);
        return trips.map(TripMapper::mapToTripDTOResponse);
    }
}
