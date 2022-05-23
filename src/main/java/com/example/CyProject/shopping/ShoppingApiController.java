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

    @PostMapping("/searchOrderByRdtDescAll")
    public List<ItemEntity> searchOrderByRdtDescAll(@RequestBody ItemDto dto) {
        System.out.println(dto.getSearch());
        return itemRepository.findByNmContainsOrderByRdtDesc(dto.getSearch());
    }
    @PostMapping("/searchOrderByRdtAscAll")
    public List<ItemEntity> searchOrderByRdtAscAll(@RequestBody ItemDto dto) {
        return itemRepository.findByNmContainsOrderByRdtAsc(dto.getSearch());
    }
    @PostMapping("/searchOrderByPriceDescAll")
    public List<ItemEntity> searchOrderByPriceDescAll(@RequestBody ItemDto dto) {
        return itemRepository.findByNmContainsOrderByPriceDesc(dto.getSearch());
    }
    @PostMapping("/searchOrderByPriceAscAll")
    public List<ItemEntity> searchOrderByPriceAscAll(@RequestBody ItemDto dto) {
        return itemRepository.findByNmContainsOrderByPriceAsc(dto.getSearch());
    }

    @PostMapping("/searchOrderByRdtDesc")
    public List<ItemEntity> searchOrderByRdtDesc(@RequestBody ItemDto dto) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(dto.getCategory(), dto.getSearch()));
        return itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(dto.getCategory(), dto.getSearch());
    }

    @PostMapping("/searchOrderByRdtAsc")
    public List<ItemEntity> searchOrderByRdtAsc(@RequestBody ItemDto dto) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByRdtAsc(dto.getCategory(), dto.getSearch()));
        return itemRepository.searchByCategoryAndTxtByOrderByRdtAsc(dto.getCategory(), dto.getSearch());
    }

    @PostMapping("/searchOrderByPriceDesc")
    public List<ItemEntity> searchOrderByPriceDesc(@RequestBody ItemDto dto) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByPriceDesc(dto.getCategory(), dto.getSearch()));
        return itemRepository.searchByCategoryAndTxtByOrderByPriceDesc(dto.getCategory(), dto.getSearch());
    }

    @PostMapping("/searchOrderByPriceAsc")
    public List<ItemEntity> searchOrderByPriceAsc(@RequestBody ItemDto dto) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByPriceAsc(dto.getCategory(), dto.getSearch()));
        return itemRepository.searchByCategoryAndTxtByOrderByPriceAsc(dto.getCategory(), dto.getSearch());
    }
}
