package com.saltyplank.webshop.services;

import com.saltyplank.webshop.dto.request.OrderRequest;
import com.saltyplank.webshop.dto.response.OrderDTO;
import com.saltyplank.webshop.dto.response.OrderItemDTO;
import com.saltyplank.webshop.exceptions.ResourceNotFoundException;
import com.saltyplank.webshop.models.*;
import com.saltyplank.webshop.repository.*;
import com.saltyplank.webshop.enums.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.saltyplank.webshop.exceptions.ForbiddenExeption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public OrderDTO create(String email, OrderRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setGebruiker(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setStreet(request.getStreet());
        order.setPostalCode(request.getPostalCode());
        order.setCity(request.getCity());
        order.setCountry(request.getCountry());

        List<OrderItem> items = request.getItems().stream().map(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

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

    public List<OrderDTO> getMyOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getById(String email, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getGebruiker().getEmail().equals(email)) {
            throw new ForbiddenExeption("Not your order");
        }

        return toDTO(order);
    }

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
                order.getStreet(),
                order.getPostalCode(),
                order.getCity(),
                order.getCountry(),
                itemDTOs
        );
    }
}