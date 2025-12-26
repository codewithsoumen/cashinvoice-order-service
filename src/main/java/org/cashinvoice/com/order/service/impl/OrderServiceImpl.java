package org.cashinvoice.com.order.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cashinvoice.com.order.dto.CreateOrderRequest;
import org.cashinvoice.com.order.dto.CreateOrderResponse;
import org.cashinvoice.com.order.model.Order;
import org.cashinvoice.com.order.repository.OrderRepository;
import org.cashinvoice.com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule()) // support java.time types
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // write ISO strings

    @Value("${order.output.dir:/input/orders}")
    private String outputDir;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();
        OffsetDateTime now = OffsetDateTime.now();

        Order order = Order.builder()
                .orderId(orderId)
                .customerId(request.getCustomerId())
                .product(request.getProduct())
                .amount(request.getAmount())
                .createdAt(now)
                .build();

        orderRepository.save(order);

        try {
            writeOrderToFile(order);
        } catch (Exception e) {
            log.error("Failed to write order to file", e);
            throw new RuntimeException("Failed to write order file", e);
        }

        log.info("Order created: {}", orderId);
        return new CreateOrderResponse(orderId, "CREATED");
    }

    private void writeOrderToFile(Order order) {
        try {
            Path dir = Path.of(outputDir);
            Files.createDirectories(dir);

            String fileName = String.format("order-%s.json", order.getOrderId());
            Path file = dir.resolve(fileName);

            String json = objectMapper.writeValueAsString(order);
            Files.writeString(file, json);

            log.info("Wrote order {} to file {}", order.getOrderId(), file.toAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to write order to file", e);
            throw new RuntimeException("Failed to write order file", e);
        }
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
    }

    @Override
    public List<Order> listOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // local exception class
    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }
}
