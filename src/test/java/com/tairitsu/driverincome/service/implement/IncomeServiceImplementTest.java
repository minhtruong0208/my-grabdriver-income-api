package com.tairitsu.driverincome.service.implement;

import com.tairitsu.driverincome.dto.response.IncomeDTOResponse;
import com.tairitsu.driverincome.repository.ExpenseRepository;
import com.tairitsu.driverincome.repository.TripRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncomeServiceImplementTest {
    @Mock
    private TripRepository tripRepository;
    @Mock
    private ExpenseRepository expenseRepository;
    @InjectMocks
    private IncomeServiceImplement incomeService;
    private LocalDate date;
    private LocalDateTime startOfDay;
    private LocalDateTime endOfDay;
    @BeforeEach
    void setUp() {
        date = LocalDate.of(2026, 5, 14);
        startOfDay = date.atStartOfDay();
        endOfDay = startOfDay.plusDays(1);
    }
    @Test
    void getDailyIncome_ShouldReturnCorrectSummary() {
        when(tripRepository.sumTripIncomeBetween(startOfDay, endOfDay)).thenReturn(BigDecimal.valueOf(300));
        when(expenseRepository.sumExpenseBetween(startOfDay, endOfDay)).thenReturn(BigDecimal.valueOf(70));
        IncomeDTOResponse response = incomeService.getDailyIncome(date);
        assertEquals(BigDecimal.valueOf(300), response.getTotalTripIncome());
        assertEquals(BigDecimal.valueOf(70), response.getTotalExpense());
        assertEquals(BigDecimal.valueOf(230), response.getNetIncome());
        assertEquals("2026-05-14", response.getPeriod());
        verify(tripRepository).sumTripIncomeBetween(startOfDay, endOfDay);
        verify(expenseRepository).sumExpenseBetween(startOfDay, endOfDay);
    }
    @Test
    void getDailyIncome_ShouldReturnZeroWhenNoData() {
        when(tripRepository.sumTripIncomeBetween(startOfDay, endOfDay)).thenReturn(BigDecimal.ZERO);
        when(expenseRepository.sumExpenseBetween(startOfDay, endOfDay)).thenReturn(BigDecimal.ZERO);
        IncomeDTOResponse response = incomeService.getDailyIncome(date);
        assertEquals(BigDecimal.ZERO, response.getTotalTripIncome());
        assertEquals(BigDecimal.ZERO, response.getTotalExpense());
        assertEquals(BigDecimal.ZERO, response.getNetIncome());
    }

    @Test
    void getDailyIncome_ShouldReturnNegativeNetIncome() {
        when(tripRepository.sumTripIncomeBetween(startOfDay, endOfDay)).thenReturn(BigDecimal.valueOf(50));
        when(expenseRepository.sumExpenseBetween(startOfDay, endOfDay)).thenReturn(BigDecimal.valueOf(100));
        IncomeDTOResponse response = incomeService.getDailyIncome(date);
        assertEquals(BigDecimal.valueOf(-50), response.getNetIncome());
    }
    @Test
    void getMonthlyIncome_ShouldReturnCorrectSummary() {
        LocalDateTime startOfMonth = LocalDate.of(2026, 5, 1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        when(tripRepository.sumTripIncomeBetween(startOfMonth, endOfMonth)).thenReturn(BigDecimal.valueOf(5000));
        when(expenseRepository.sumExpenseBetween(startOfMonth, endOfMonth)).thenReturn(BigDecimal.valueOf(1200));
        IncomeDTOResponse response = incomeService.getMonthlyIncome(2026, 5);
        assertEquals(BigDecimal.valueOf(5000), response.getTotalTripIncome());
        assertEquals(BigDecimal.valueOf(1200), response.getTotalExpense());
        assertEquals(BigDecimal.valueOf(3800), response.getNetIncome());
        assertEquals("2026-05", response.getPeriod());
        verify(tripRepository).sumTripIncomeBetween(startOfMonth, endOfMonth);
        verify(expenseRepository).sumExpenseBetween(startOfMonth, endOfMonth);
    }
    @Test
    void getMonthlyIncome_ShouldReturnZeroWhenNoData() {
        LocalDateTime startOfMonth = LocalDate.of(2026, 5, 1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        when(tripRepository.sumTripIncomeBetween(startOfMonth, endOfMonth)).thenReturn(BigDecimal.ZERO);
        when(expenseRepository.sumExpenseBetween(startOfMonth, endOfMonth)).thenReturn(BigDecimal.ZERO);
        IncomeDTOResponse response = incomeService.getMonthlyIncome(2026, 5);
        assertEquals(BigDecimal.ZERO, response.getTotalTripIncome());
        assertEquals(BigDecimal.ZERO, response.getTotalExpense());
        assertEquals(BigDecimal.ZERO, response.getNetIncome());
    }
    @Test
    void getMonthlyIncome_ShouldReturnNegativeNetIncome() {
        LocalDateTime startOfMonth = LocalDate.of(2026, 5, 1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        when(tripRepository.sumTripIncomeBetween(startOfMonth, endOfMonth)).thenReturn(BigDecimal.valueOf(500));
        when(expenseRepository.sumExpenseBetween(startOfMonth, endOfMonth)).thenReturn(BigDecimal.valueOf(1200));
        IncomeDTOResponse response = incomeService.getMonthlyIncome(2026, 5);
        assertEquals(BigDecimal.valueOf(-700), response.getNetIncome());
    }
}