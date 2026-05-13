package com.tairitsu.driverincome.repository;

import com.tairitsu.driverincome.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Repository for managing expense persistence operations.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    /**
     * Calculates total expense amount within the specified time range.
     * <p>The start time is inclusive and the end time is exclusive.
     * <p>If no expenses are found, this query returns {@link BigDecimal#ZERO}.
     * @param startOfDay range start timestamp (inclusive)
     * @param endOfDay range end timestamp (exclusive)
     * @return summed expense amount
     */
    @Query("""
    SELECT COALESCE(SUM(e.amount), 0)
    FROM Expense e
    WHERE e.expenseDate >= :startOfDay
    AND e.expenseDate < :endOfDay
""")
    BigDecimal sumExpenseBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
