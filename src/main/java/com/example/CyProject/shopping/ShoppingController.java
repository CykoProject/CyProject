package com.example.CyProject.shopping;

import com.example.CyProject.config.AuthenticationFacade;
import com.example.CyProject.shopping.model.cart.CartRepository;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String shoppingMain(Model model, @PageableDefault(size=5) Pageable pageable) {
        model.addAttribute("items", itemRepository.findAllByOrderByRdtDesc(pageable));
        model.addAttribute("itemsCount", itemRepository.findAllByOrderByRdtDesc().size());
        System.out.println("main : " + itemRepository.findAllByOrderByRdtDesc(pageable));
        return "shopping/shoppingMain";
    }

    @GetMapping("/search")
    public String shoppingSearch(@RequestParam int category, String search, Model model, @PageableDefault(size=5) Pageable pageable) {
        if(category == 0) {
            model.addAttribute("items", itemRepository.findByNmContainsOrderByRdtDesc(search, pageable));
            model.addAttribute("itemsCount", itemRepository.findByNmContainsOrderByRdtDesc(search).size());
            List<ItemEntity> result = itemRepository.findByNmContainsOrderByRdtDesc(search, pageable);
        } else {
            model.addAttribute("items", itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(category, search, pageable));
            model.addAttribute("itemsCount", itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(category, search).size());
            List<ItemEntity> result = itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(category, search, pageable);
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
