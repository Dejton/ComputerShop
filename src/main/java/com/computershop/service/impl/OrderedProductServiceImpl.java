package com.computershop.service.impl;

import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.dto.OrderedProductDto;
import com.computershop.repository.OrderedProductRepository;
import com.computershop.service.OrderedProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderedProductServiceImpl implements OrderedProductService {
    private final OrderedProductRepository orderedProductRepository;

    public OrderedProductServiceImpl(OrderedProductRepository orderedProductRepository) {
        this.orderedProductRepository = orderedProductRepository;
    }

    @Override
    public OrderedProduct addOrderedProduct(OrderedProductDto orderedProductDto) {
        return orderedProductRepository.save(OrderedProductDto.matFromDto(orderedProductDto));
    }

    @Override
    public void deleteOrderedProductById(long id) {
        orderedProductRepository.deleteById(id);
    }

    @Override
    public List<OrderedProductDto> getAllOrderedProducts() {
        List<OrderedProduct> orderedProducts = orderedProductRepository.findAll();
        return orderedProducts.stream()
                .map(OrderedProductDto::matToDto)
                .toList();
    }

    @Override
    public OrderedProductDto getOrderedProductById(long id) {
        if (id < 0) throw new IllegalArgumentException("Enter correct value!");
        return OrderedProductDto.matToDto(orderedProductRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Ordered product with id: " + id + " does not exist!")));
    }
}
