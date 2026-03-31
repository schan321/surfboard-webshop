package com.saltyplank.webshop.services;

import com.saltyplank.webshop.dto.request.OrderRequest;
import com.saltyplank.webshop.dto.response.OrderDTO;
import com.saltyplank.webshop.dto.response.OrderItemDTO;
import com.saltyplank.webshop.models.*;
import com.saltyplank.webshop.repository.*;
import com.saltyplank.webshop.enums.OrderStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final GebruikerRepository gebruikerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        GebruikerRepository gebruikerRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.gebruikerRepository = gebruikerRepository;
        this.productRepository = productRepository;
    }

    // Place order
    public OrderDTO create(String email, OrderRequest request) {
        Gebruiker gebruiker = gebruikerRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Order order = new Order();
        order.setGebruiker(gebruiker);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(request.getShippingAddress());

        List<OrderItem> items = request.getItems().stream().map(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(product.getPrice());
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);

        Double total = items.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
        order.setTotal(total);

        orderRepository.save(order);
        return toDTO(order);
    }

    // Get orders
    public List<OrderDTO> getMyOrders(String email) {
        Gebruiker gebruiker = gebruikerRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return orderRepository.findByGebruikerId(gebruiker.getId())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Get order by id
    public OrderDTO getById(String email, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (!order.getGebruiker().getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your order");
        }

        return toDTO(order);
    }

    // Convert to DTO
    private OrderDTO toDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProduct().getName(),
                        item.getUnitPrice(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getCreatedAt(),
                order.getTotal(),
                order.getStatus(),
                order.getShippingAddress(),
                itemDTOs
        );
    }
}
