package com.tairitsu.driverincome.mapper;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;
import com.tairitsu.driverincome.entity.Trip;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TripMapper {
    public static Trip mapToTrip(TripDTORequest req) {
        Trip trip = new Trip();
        trip.setStartTime(req.getStartTime());
        trip.setNetIncome(req.getNetIncome());
        trip.setTip(req.getTip());
        trip.setDistance(req.getDistance());
        trip.setTypeOfTrip(req.getTypeOfTrip());
        return trip;
    }

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

    public static void updateTripFromRequest(TripDTORequest req, Trip trip) {
        trip.setStartTime(req.getStartTime());
        trip.setNetIncome(req.getNetIncome());
        trip.setTip(req.getTip() != null ? req.getTip() : BigDecimal.ZERO);
        trip.setDistance(req.getDistance());
        trip.setTypeOfTrip(req.getTypeOfTrip());
    }
}
