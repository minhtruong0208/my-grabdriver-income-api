package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.request.TripDTORequest;
import com.tairitsu.driverincome.dto.response.TripDTOResponse;
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

/**
 * Service implementation for managing trip records.
 * <p>This service handles:
 * <ul>
 *     <li>Trip CRUD operations</li>
 *     <li>Total income calculation</li>
 *     <li>Pagination support</li>
 *     <li>DTO and entity mapping</li>
 * </ul>
 */
@SuppressWarnings("unused")
@Service
public class TripServiceImplement implements TripService {
    private final TripRepository tripRepository;
    public TripServiceImplement(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
    /**
     * Creates a new trip record and calculates the total income.
     * <p>If tip is null, it will default to {@link BigDecimal#ZERO}.
     * Total income is calculated as:
     * <pre>
     * total = netIncome + tip
     * </pre>
     * @param req request object containing trip information
     * @return created trip response
     */
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
    /**
     * Deletes a trip by its identifier.
     * @param id trip identifier
     * @throws ResourceNotFoundException if the trip does not exist
     */
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
    /**
     * Retrieves all trip records.
     * @return list of trip responses
     */
    @Override
    public List<TripDTOResponse> getAllTrip(){
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(TripMapper::mapToTripDTOResponse).toList();
    }
    /**
     * Updates an existing trip and recalculates total income.
     * <p>If tip is null, it will default to {@link BigDecimal#ZERO}.
     * @param id trip identifier
     * @param req updated trip information
     * @return updated trip response
     * @throws ResourceNotFoundException if the trip does not exist
     */
    @Override
    public TripDTOResponse updateTrip(Long id, TripDTORequest req) {
        Trip existedTrip = tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip not found with id = " + id));
        TripMapper.updateTripFromRequest(req, existedTrip);
        BigDecimal tip = Optional.ofNullable(existedTrip.getTip()).orElse(BigDecimal.ZERO);
        existedTrip.setTip(tip);
        existedTrip.setTotal(existedTrip.getNetIncome().add(tip));
        Trip udatedTrip = tripRepository.save(existedTrip);
        return TripMapper.mapToTripDTOResponse(udatedTrip);
    }
    /**
     * Retrieves paginated trip records.
     * @param page zero-based page index
     * @param size number of records per page
     * @return paginated trip responses
     */
    @Override
    public Page<TripDTOResponse> getTrips(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Trip> trips = tripRepository.findAll(pageable);
        return trips.map(TripMapper::mapToTripDTOResponse);
    }
}
