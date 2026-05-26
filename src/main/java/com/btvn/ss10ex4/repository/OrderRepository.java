package com.btvn.ss10ex4.repository;

import com.btvn.ss10ex4.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
