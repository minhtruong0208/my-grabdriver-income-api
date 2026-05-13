package com.tairitsu.driverincome.dto.request;

import com.tairitsu.driverincome.entity.ExpenseType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDTORequest {
    @NotNull(message = "Hao phí ròng không được để trống")
    @Positive(message = "Hao phí phải > 0")
    private BigDecimal amount;
    @NotNull(message = "Loại hao phí không được để trống")
    private ExpenseType typeOfExpense;
    @NotNull(message = "Ngày hao phí không được để trống")
    private LocalDateTime expenseDate;
    private String note;
}
