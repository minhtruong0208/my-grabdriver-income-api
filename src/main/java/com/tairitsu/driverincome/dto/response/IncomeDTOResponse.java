package com.tairitsu.driverincome.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class IncomeDTOResponse {
    private BigDecimal totalTripIncome;
    private BigDecimal totalExpense;
    private BigDecimal netIncome;
    private String period;
}
