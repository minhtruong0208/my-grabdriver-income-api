package com.tairitsu.driverincome.repository;

import com.tairitsu.driverincome.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
