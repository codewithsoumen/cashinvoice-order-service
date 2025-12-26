package org.cashinvoice.com.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cashinvoice.com.order.dto.CreateOrderRequest;
import org.cashinvoice.com.order.dto.CreateOrderResponse;
import org.cashinvoice.com.order.model.Order;
import org.cashinvoice.com.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order")
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.debug("Received create order request: {}", request);
        CreateOrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get order by id")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "List orders by customer (optional customerId)")
    @GetMapping
    public ResponseEntity<List<Order>> listOrdersByCustomer(@RequestParam(required = false) String customerId) {
        if (customerId == null) {
            return ResponseEntity.ok(List.of());
        }
        List<Order> orders = orderService.listOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }
}
