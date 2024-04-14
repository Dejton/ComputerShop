package com.computershop.service.impl;

import com.computershop.model.entity.Order;
import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.entity.Product;
import com.computershop.model.entity.User;
import com.computershop.model.dto.OrderDto;
import com.computershop.model.dto.OrderedProductDto;
import com.computershop.repository.OrderRepository;
import com.computershop.repository.ProductRepository;
import com.computershop.repository.UserRepository;
import com.computershop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
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

    @Override
    public List<OrderedProductDto> getAllProductsInCart(User user, String status) {
        Optional<Order> orderInProgress = orderRepository.findByUserAndStatus(user, "In progress");
        if (orderInProgress.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<OrderedProduct> orderedProducts = orderInProgress.get().getOrderedProducts();
            return orderedProducts.stream()
                    .map(OrderedProductDto::matToDto)
                    .toList();
        }
    }

    @Override
    public void realizeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("order with id: " + orderId + " not found"));
        for (OrderedProduct orderedProduct: order.getOrderedProducts()) {
            Product product = orderedProduct.getProduct();
            int orderedQuantity = orderedProduct.getQuantity();
            if (product.getAmountInMagazine() >= orderedQuantity) {
                product.setAmountInMagazine(product.getAmountInMagazine() - orderedQuantity);
                productRepository.save(product);
            } else throw new IllegalArgumentException("Not enough products in magazine!");
        }
        order.setStatus("Order in progress");
        orderRepository.save(order);
    }

    @Override
    public OrderDto getOrderByUserAndStatus(User user, String status) {
        return OrderDto.mapToDto(orderRepository.findByUserAndStatus(user,status).orElseThrow(() -> new EntityNotFoundException("no order in progress for that user")));
    }


}
