package com.btvn.ss10ex4.model.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoDto {
    @NotBlank(message = "Mã giảm giá không được để trống")
    private String code;

    @NotNull(message = "Tỷ lệ giảm giá không được để trống")
    @Min(value = 1, message = "Tỷ lệ giảm giá phải lớn hơn hoặc bằng 1%")
    @Max(value = 100, message = "Tỷ lệ giảm giá không được vượt quá 100%")
    private Integer discountPercent;

    private Boolean isActive = true;
}