package com.example.CyProject.shopping;

import com.example.CyProject.shopping.model.item.ItemDto;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.item.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping/api")
public class ShoppingApiController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/allOrderByRdtDesc")
    public List<ItemEntity> allOrderByRdtDesc() {
        return itemRepository.findAllByOrderByRdtDesc();
    }

    @GetMapping("/allOrderByRdtAsc")
    public List<ItemEntity> allOrderByRdtAsc() {
        return itemRepository.findAllByOrderByRdtAsc();
    }

    @GetMapping("/allOrderByPriceDesc")
    public List<ItemEntity> allOrderByPriceDesc() {
        return itemRepository.findAllByOrderByPriceDesc();
    }

    @GetMapping("/allOrderByPriceAsc")
    public List<ItemEntity> allOrderByPriceAsc() {
        return itemRepository.findAllByOrderByPriceAsc();
    }

    @PostMapping("/searchOrderByRdtDesc")
    public List<ItemEntity> searchOrderByRdtDesc(@RequestBody ItemDto dto) {
        System.out.println(dto.getSearch());
        System.out.println(dto.getCategory());
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(dto.getCategory(), dto.getSearch()));
        return itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(dto.getCategory(), dto.getSearch());
    }

//    @PostMapping("/searchOrderByRdtAsc")
//    public List<ItemEntity> searchOrderByRdtAsc(String category, String search) {
//        return itemRepository.searchByCategoryAndTxtByOrderByRdtAsc(category, search);
//    }
//
//    @PostMapping("/searchOrderByPriceDesc")
//    public List<ItemEntity> searchOrderByPriceDesc(String category, String search) {
//        return itemRepository.searchByCategoryAndTxtByOrderByPriceDesc(category, search);
//    }
//
//    @PostMapping("/searchOrderByPriceAsc")
//    public List<ItemEntity> searchOrderByPriceAsc(String category, String search) {
//        return itemRepository.searchByCategoryAndTxtByOrderByPriceAsc(category, search);
//    }
}
