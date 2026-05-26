package com.btvn.ss10ex4.controller;

import com.btvn.ss10ex4.model.Order;
import com.btvn.ss10ex4.model.Promotion;
import com.btvn.ss10ex4.model.dto.request.PromoDto;
import com.btvn.ss10ex4.model.dto.respponse.ApiDataResponse;
import com.btvn.ss10ex4.service.PromotionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping("/promo")
    public ResponseEntity<ApiDataResponse<Promotion>> createPromo(@Valid @RequestBody PromoDto dto) {
        Promotion p = new Promotion();
        p.setCode(dto.getCode());
        p.setDiscountPercent(dto.getDiscountPercent());
        p.setIsActive(dto.getIsActive());

        Promotion result = promotionService.createPromotion(p);
        return ResponseEntity.ok(ApiDataResponse.<Promotion>builder().success(true).message("Tạo khuyến mãi thành công").data(result).httpStatus(HttpStatus.OK).build());
    }

    @PostMapping("/promo/apply")
    public ResponseEntity<ApiDataResponse<Order>> applyPromo(@RequestBody Map<String, Object> body) {
        Long orderId = Long.valueOf(body.get("orderId").toString());
        String promoCode = body.get("promoCode").toString();

        Order updatedOrder = promotionService.applyPromotion(orderId, promoCode);
        return ResponseEntity.ok(ApiDataResponse.<Order>builder().success(true).message("Áp dụng mã giảm giá thành công").data(updatedOrder).httpStatus(HttpStatus.OK).build());
    }
}