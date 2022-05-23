package com.example.CyProject.shopping;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    AuthenticationFacade authenticationFacade;

    @GetMapping("")
    public String shoppingMain(Model model) {
        model.addAttribute("items", itemRepository.findAllByOrderByRdtDesc());
        System.out.println("main : " + itemRepository.findAllByOrderByRdtDesc());
        return "shopping/shoppingMain";
    }

    @GetMapping("/search")
    public String shoppingSearch(@RequestParam int category, String search, Model model) {
        if(category == 0) {
            model.addAttribute("items", itemRepository.findByNmContainsOrderByRdtDesc(search));
            List<ItemEntity> result = itemRepository.findByNmContainsOrderByRdtDesc(search);
        } else {
            model.addAttribute("items", itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(category, search));
            List<ItemEntity> result = itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(category, search);
        }
        return "shopping/shoppingMain";
    }

    @GetMapping("/kakaoPayCancel")
    public String kakaoPayCancel() {

        return"/shopping/kakaoPayCancel";
    }

    @GetMapping("/kakaoPaySuccessFail")
    public String kakaoPaySuccessFail() {

        return "/shopping/kakaoPaySuccessFail";
    }
}
