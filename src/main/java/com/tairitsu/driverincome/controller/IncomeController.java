package com.tairitsu.driverincome.controller;

import com.tairitsu.driverincome.dto.response.IncomeDTOResponse;
import com.tairitsu.driverincome.service.IncomeService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {
    private final IncomeService incomeService;
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }
    @GetMapping("/daily")
    public IncomeDTOResponse getDailyIncome(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return incomeService.getDailyIncome(date);
    }
    @GetMapping("/monthly")
    public IncomeDTOResponse getMonthlyIncome(@RequestParam int year, @RequestParam int month) {
        return incomeService.getMonthlyIncome(year, month);
    }
}