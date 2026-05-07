package com.tairitsu.driverincome.controller;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;
import com.tairitsu.driverincome.service.TripService;
import jakarta.validation.Valid;
import lombok.NonNull;
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
    @GetMapping
    public ResponseEntity<@NonNull List<TripDTOResponse>> getAllTrip() {
        List<TripDTOResponse> trips = tripService.getAllTrip();
        return ResponseEntity.ok(trips);
    }
    @PutMapping("/{id}")
    public ResponseEntity<@NonNull TripDTOResponse> updateTrip(@PathVariable Long id, @Valid @RequestBody TripDTORequest req) {
        return ResponseEntity.ok(tripService.updateTrip(id, req));
    }
}