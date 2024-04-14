package com.computershop.service;

import com.computershop.model.entity.OrderedProduct;
import com.computershop.model.dto.OrderedProductDto;

import java.util.List;

public interface OrderedProductService {
    OrderedProduct addOrderedProduct(OrderedProductDto orderedProductDto);
    void deleteOrderedProductById(long id);
    List<OrderedProductDto> getAllOrderedProducts();
    OrderedProductDto getOrderedProductById(long id);
}
