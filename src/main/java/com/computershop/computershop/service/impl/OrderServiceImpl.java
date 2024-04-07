package com.computershop.computershop.service.impl;

import com.computershop.computershop.entity.Order;
import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.Product;
import com.computershop.computershop.entity.User;
import com.computershop.computershop.entity.dto.OrderDto;
import com.computershop.computershop.entity.dto.OrderedProductDto;
import com.computershop.computershop.repository.OrderRepository;
import com.computershop.computershop.repository.ProductRepository;
import com.computershop.computershop.repository.UserRepository;
import com.computershop.computershop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order addOrder(OrderDto orderDto) {
        return orderRepository.save(OrderDto.mapFromDto(orderDto));
    }

    @Override
    public void deleteOrderById(long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderDto::mapToDto)
                .toList();
    }

    @Override
    public OrderDto getOrderById(long id) {
        if (id < 0) throw new IllegalArgumentException("Enter correct value!");
        return OrderDto.mapToDto(orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with id: " + id + "not found!")));
    }

    @Override
    public ResponseEntity<String> addProductToOrder(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().body("Product with id: " + productId + " not found!");
        }
        if (product.getAmountInMagazine() < quantity) {
            return ResponseEntity.badRequest().body("Not enough products in magazine!");
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User with id: " + userId + " not found!");
        }
        Order order = orderRepository.findByUserAndStatus(user, "In progress").orElse(null);

        OrderedProduct orderedProduct = new OrderedProduct();
        orderedProduct.setQuantity(quantity);
        orderedProduct.setProduct(product);
        orderedProduct.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        if (order != null) {
            orderedProduct.setOrder(order);
            order.getOrderedProducts().add(orderedProduct);
            order.setTotalValue(order.getTotalValue().add(orderedProduct.getPrice()));
            orderRepository.save(order);
        } else {
            Order newOrder = Order.builder()
                    .user(user)
                    .orderedProducts(List.of(orderedProduct))
                    .deliveryAddress(user.getAddress())
                    .totalValue(orderedProduct.getPrice())
                    .dateOfOrder(LocalDate.now())
                    .status("In progress")
                    .build();
            orderedProduct.setOrder(newOrder);
            orderRepository.save(newOrder);
        }

        return ResponseEntity.ok("Product successfully added to your order!");
    }

    @Override
    public ResponseEntity<String> updateOrderStatus(Long orderId, String newStatus) {
        Order updatedOrder = orderRepository.findById(orderId).orElse(null);
        if (updatedOrder == null) {
            return ResponseEntity.badRequest().body("Order with id: " + orderId + " not found!");
        }
        updatedOrder.setStatus(newStatus);
        orderRepository.save(updatedOrder);
        return ResponseEntity.ok("The order status has been successfully updated.");
    }




}
