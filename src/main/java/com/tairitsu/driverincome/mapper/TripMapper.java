package com.tairitsu.driverincome.mapper;

import com.tairitsu.driverincome.dto.request.TripDTORequest;
import com.tairitsu.driverincome.dto.response.TripDTOResponse;
import com.tairitsu.driverincome.entity.Trip;

import java.math.BigDecimal;

/**
 * Utility class for mapping between Trip entities and DTOs.
 */
public class TripMapper {
    /**
     * Maps a trip request DTO to a Trip entity.
     * @param req trip request data
     * @return mapped trip entity
     */
    public static Trip mapToTrip(TripDTORequest req) {
        Trip trip = new Trip();
        trip.setStartTime(req.getStartTime());
        trip.setNetIncome(req.getNetIncome());
        trip.setTip(req.getTip());
        trip.setDistance(req.getDistance());
        trip.setTypeOfTrip(req.getTypeOfTrip());
        return trip;
    }
    /**
     * Maps a Trip entity to a response DTO.
     * @param trip trip entity
     * @return mapped trip response
     */
    public static TripDTOResponse mapToTripDTOResponse(Trip trip) {
        return new TripDTOResponse(
                trip.getId(),
                trip.getStartTime(),
                trip.getNetIncome(),
                trip.getTip(),
                trip.getTotal(),
                trip.getDistance(),
                trip.getTypeOfTrip()
        );
    }
    /**
     * Updates mutable trip fields from request data.
     * <p>If tip is null, it defaults to {@link BigDecimal#ZERO}.
     * @param req source request data
     * @param trip target trip entity to update
     */
    public static void updateTripFromRequest(TripDTORequest req, Trip trip) {
        trip.setStartTime(req.getStartTime());
        trip.setNetIncome(req.getNetIncome());
        trip.setTip(req.getTip() != null ? req.getTip() : BigDecimal.ZERO);
        trip.setDistance(req.getDistance());
        trip.setTypeOfTrip(req.getTypeOfTrip());
    }
}
