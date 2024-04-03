package com.computershop.computershop.service;

import com.computershop.computershop.entity.OrderedProduct;
import com.computershop.computershop.entity.dto.OrderedProductDto;

import java.util.List;

public interface OrderedProductService {
    OrderedProduct addOrderedProduct(OrderedProductDto orderedProductDto);
    void deleteOrderedProductById(long id);
    List<OrderedProductDto> getAllOrderedProducts();
    OrderedProductDto getOrderedProductById(long id);
}
