package com.saltyplank.webshop.controllers;

import com.saltyplank.webshop.dto.request.OrderRequest;
import com.saltyplank.webshop.dto.response.OrderDTO;
import com.saltyplank.webshop.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> create(@Valid @RequestBody OrderRequest request,
                                           Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.create(principal.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getMyOrders(Principal principal) {
        return ResponseEntity.ok(orderService.getMyOrders(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(orderService.getById(principal.getName(), id));
    }
}