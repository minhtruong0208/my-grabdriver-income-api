package com.tairitsu.driverincome.controller;

import com.tairitsu.driverincome.dto.request.TripDTORequest;
import com.tairitsu.driverincome.dto.response.TripDTOResponse;
import com.tairitsu.driverincome.service.TripService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }
    @PostMapping
    public ResponseEntity<@NonNull TripDTOResponse> addTrip(@Valid @RequestBody TripDTORequest req) {
        return new ResponseEntity<>(tripService.createTrip(req), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<@NonNull TripDTOResponse> getTrip(@PathVariable Long id) {
        TripDTOResponse trip = tripService.getTrip(id);
        return ResponseEntity.ok(trip);
    }
    @GetMapping("/all")
    public ResponseEntity<@NonNull List<TripDTOResponse>> getAllTrip() {
        List<TripDTOResponse> trips = tripService.getAllTrip();
        return ResponseEntity.ok(trips);
    }
    @PutMapping("/{id}")
    public ResponseEntity<@NonNull TripDTOResponse> updateTrip(@PathVariable Long id, @Valid @RequestBody TripDTORequest req) {
        return ResponseEntity.ok(tripService.updateTrip(id, req));
    }
    @GetMapping
    public ResponseEntity<Page<TripDTOResponse>> getTrips(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index cannot be negative") int page,
            @RequestParam(defaultValue = "10") @Positive(message = "Page size must be greater than zero") int size) {
        return ResponseEntity.ok(tripService.getTrips(page, size));
    }
}