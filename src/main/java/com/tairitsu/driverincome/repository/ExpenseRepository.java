package com.tairitsu.driverincome.repository;

import com.tairitsu.driverincome.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
