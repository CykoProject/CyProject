package com.example.CyProject.shopping;

import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    ItemRepository itemRepository;

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

//    @GetMapping("/allOrderByRdtDesc")
//    public String allOrderByRdtDesc() {
//        return itemRepository.findAllByOrderByRdtDesc();
//    }
//
//    @GetMapping("/allOrderByRdtAsc")
//    public String allOrderByRdtAsc() {
//        return itemRepository.findAllByOrderByRdtAsc();
//    }
//
//    @GetMapping("/allOrderByPriceDesc")
//    public String allOrderByPriceDesc() {
//        return itemRepository.findAllByOrderByPriceDesc();
//    }
//
//    @GetMapping("/allOrderByPriceAsc")
//    public String allOrderByPriceAsc() {
//        return itemRepository.findAllByOrderByPriceAsc();
//    }
//
//    @GetMapping("/searchOrderByRdtDesc")
//    public String searchOrderByRdtDesc(int icategory, String search) {
//        return itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(icategory, search);
//    }
//
//    @GetMapping("/searchOrderByRdtAsc")
//    public String searchOrderByRdtAsc(int icategory, String search) {
//        return itemRepository.searchByCategoryAndTxtByOrderByRdtAsc(icategory, search);
//    }
//
//    @GetMapping("/searchOrderByPriceDesc")
//    public String searchOrderByPriceDesc(int icategory, String search) {
//        return itemRepository.searchByCategoryAndTxtByOrderByPriceDesc(icategory, search);
//    }
//
//    @GetMapping("/searchOrderByPriceAsc")
//    public List<ItemEntity> searchOrderByPriceAsc(int icategory, String search) {
//        return itemRepository.searchByCategoryAndTxtByOrderByPriceAsc(icategory, search);
//    }

    @GetMapping("/cart")
    public String shoppingCart() {

        return "shopping/shoppingCart";
    }
}
