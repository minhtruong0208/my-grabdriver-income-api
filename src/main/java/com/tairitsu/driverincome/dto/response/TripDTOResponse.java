package com.tairitsu.driverincome.dto.response;

import com.tairitsu.driverincome.entity.TripType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDTOResponse {
    private Long id;
    private LocalDateTime startTime;
    @NotNull(message = "Thu nhập ròng không được để trống")
    @Positive(message = "Thu nhập ròng phải > 0")
    private BigDecimal netIncome;
    @PositiveOrZero(message = "Tiền bo không được âm")
    private BigDecimal tip;
    @NotNull
    @Positive
    private BigDecimal total;
    @NotNull(message = "Độ dài quãng đường không được để trống")
    @Positive(message = "Độ dài quãng đường phải > 0")
    private BigDecimal distance;
    private TripType typeOfTrip;
}
