package com.tairitsu.driverincome.repository;

import com.tairitsu.driverincome.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("""
    SELECT COALESCE(SUM(e.amount), 0)
    FROM Expense e
    WHERE e.expenseDate >= :startOfDay
    AND e.expenseDate < :endOfDay
""")
    BigDecimal sumExpenseBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
