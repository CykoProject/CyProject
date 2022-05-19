package com.example.CyProject.shopping;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping/cart")
public class CartController {

    @Autowired
    AuthenticationFacade authenticationFacade;
    @Autowired
    CartRepository cartRepository;

    @GetMapping("")
    public String shoppingCart(Model model) {

        UserEntity entity = new UserEntity();
        entity.setIuser(authenticationFacade.getLoginUserPk());

        model.addAttribute("items", cartRepository.findAllByIuser(entity));
        if (cartRepository.totalPrice(entity) == null) {
            model.addAttribute("totalPrice", 0);
            System.out.println(cartRepository.findAllByIuser(entity));
        } else {
            model.addAttribute("totalPrice", cartRepository.totalPrice(entity));
        }
        System.out.println(cartRepository.totalPrice(entity));

        return "shopping/shoppingCart";
    }
}
