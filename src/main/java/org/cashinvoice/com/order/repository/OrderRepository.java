package org.cashinvoice.com.order.repository;

import org.cashinvoice.com.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String orderId);
    List<Order> findByCustomerId(String customerId);
}

