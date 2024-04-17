package com.computershop.controller.web;

import com.computershop.model.dto.ProductDto;
import com.computershop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductInfoController {
    private final ProductService productService;

    public ProductInfoController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/product-info/{id}")
    public String showProductInfo(Model model, @PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        int amountInMagazine = product.getAmountInMagazine();
        List<String> images = product.getImages();
        model.addAttribute("product", product);
        model.addAttribute("images", images);
        model.addAttribute("amountInMagazine", amountInMagazine);
        return "productInfo";
    }
}
