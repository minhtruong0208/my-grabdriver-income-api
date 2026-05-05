package com.tairitsu.driverincome.dto;

import com.tairitsu.driverincome.entity.TripType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDTORequest {
    private LocalDateTime startTime;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal netIncome;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal tip;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal distance;
    private TripType typeOfTrip;
}
