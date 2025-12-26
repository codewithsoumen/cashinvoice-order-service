package org.cashinvoice.com.order.service;

import org.cashinvoice.com.order.dto.CreateOrderRequest;
import org.cashinvoice.com.order.dto.CreateOrderResponse;
import org.cashinvoice.com.order.model.Order;

import java.util.List;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    Order getOrderById(String orderId);
    List<Order> listOrdersByCustomer(String customerId);
}

