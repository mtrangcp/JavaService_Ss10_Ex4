package com.btvn.ss10ex4.service;

import com.btvn.ss10ex4.exception.BusinessException;
import com.btvn.ss10ex4.model.Order;
import com.btvn.ss10ex4.model.Promotion;
import com.btvn.ss10ex4.repository.OrderRepository;
import com.btvn.ss10ex4.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final OrderRepository orderRepository;

    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public Order createOrder(Order order) {
        order.setDiscountAmount(0.0);
        order.setFinalAmount(order.getTotalAmount());
        return orderRepository.save(order);
    }

    @Transactional
    public Order applyPromotion(Long orderId, String promoCode) {

        if ("CRASH".equalsIgnoreCase(promoCode)) {
            throw new NullPointerException("Simulated System Crash: Lỗi con trỏ null tại hạ tầng!");
        }

        if ("EXPIRED".equalsIgnoreCase(promoCode)) {
            throw new BusinessException("PROMO_INVALID", "Mã giảm giá này đã hết hạn sử dụng!");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("ORDER_NOT_FOUND", "Không tìm thấy đơn hàng tương ứng!"));

        Promotion promotion = promotionRepository.findByCode(promoCode)
                .orElseThrow(() -> new BusinessException("PROMO_INVALID", "Mã giảm giá không tồn tại trên hệ thống!"));

        if (!promotion.getIsActive()) {
            throw new BusinessException("PROMO_INVALID", "Mã giảm giá hiện tại đã bị vô hiệu hóa!");
        }

        double discountAmount = (order.getTotalAmount() * promotion.getDiscountPercent()) / 100.0;
        double finalAmount = order.getTotalAmount() - discountAmount;

        order.setDiscountAmount(discountAmount);
        order.setFinalAmount(finalAmount);

        return orderRepository.save(order);
    }
}