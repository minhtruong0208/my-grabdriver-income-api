package com.tairitsu.driverincome.repository;

import com.tairitsu.driverincome.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("""
    SELECT COALESCE(SUM(t.total), 0)
    FROM Trip t
    WHERE t.startTime >= :startOfDay
    AND t.startTime < :endOfDay
""")
    BigDecimal sumTripIncomeBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}


