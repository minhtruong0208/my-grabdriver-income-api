package com.tairitsu.driverincome.service;

import com.tairitsu.driverincome.dto.response.IncomeDTOResponse;
import java.time.LocalDate;

public interface IncomeService {
    IncomeDTOResponse getDailyIncome(LocalDate date);
    IncomeDTOResponse getMonthlyIncome(int year, int month);
}