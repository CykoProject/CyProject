package com.example.CyProject.shopping;

import com.example.CyProject.shopping.model.cart.CartEntity;
import com.example.CyProject.shopping.model.cart.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartApiController {

    @Autowired
    CartRepository cartRepository;

    @PostMapping
    public void saveItem(@RequestBody CartEntity entity) {
        cartRepository.save(entity);
    }
}
