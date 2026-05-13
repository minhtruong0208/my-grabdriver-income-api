package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.response.IncomeDTOResponse;
import com.tairitsu.driverincome.repository.ExpenseRepository;
import com.tairitsu.driverincome.repository.TripRepository;
import com.tairitsu.driverincome.service.IncomeService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class IncomeServiceImplement implements IncomeService {
    private final TripRepository tripRepository;
    private final ExpenseRepository expenseRepository;
    public IncomeServiceImplement(TripRepository tripRepository, ExpenseRepository expenseRepository) {
        this.tripRepository = tripRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public IncomeDTOResponse getDailyIncome(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        BigDecimal totalTripIncome = tripRepository.sumTripIncomeBetween(startOfDay, endOfDay);
        BigDecimal totalExpense = expenseRepository.sumExpenseBetween(startOfDay, endOfDay);
        BigDecimal netIncome = totalTripIncome.subtract(totalExpense);
        return new IncomeDTOResponse(totalTripIncome, totalExpense, netIncome, date.toString());
    }
    @Override
    public IncomeDTOResponse getMonthlyIncome(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        LocalDateTime startOfMonth = LocalDate.of(year, month, 1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        BigDecimal totalTripIncome = tripRepository.sumTripIncomeBetween(startOfMonth, endOfMonth);
        BigDecimal totalExpense = expenseRepository.sumExpenseBetween(startOfMonth, endOfMonth);
        BigDecimal netIncome = totalTripIncome.subtract(totalExpense);
        String period = String.format("%d-%02d", year, month);
        return new IncomeDTOResponse(totalTripIncome, totalExpense, netIncome, period);
    }
}