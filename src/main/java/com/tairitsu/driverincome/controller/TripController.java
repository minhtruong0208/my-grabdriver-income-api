package com.tairitsu.driverincome.controller;

import com.tairitsu.driverincome.dto.TripDTORequest;
import com.tairitsu.driverincome.dto.TripDTOResponse;
import com.tairitsu.driverincome.service.TripService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}