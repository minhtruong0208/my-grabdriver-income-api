package com.tairitsu.driverincome.repository;

import com.tairitsu.driverincome.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Repository for managing trip persistence operations.
 */
public interface TripRepository extends JpaRepository<Trip, Long> {
    /**
     * Calculates total trip income within the specified time range.
     * <p>The start time is inclusive and the end time is exclusive.
     * <p>If no trips are found, this query returns {@link BigDecimal#ZERO}.
     * @param startOfDay range start timestamp (inclusive)
     * @param endOfDay range end timestamp (exclusive)
     * @return summed trip income
     */
    @Query("""
    SELECT COALESCE(SUM(t.total), 0)
    FROM Trip t
    WHERE t.startTime >= :startOfDay
    AND t.startTime < :endOfDay
""")
    BigDecimal sumTripIncomeBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}


