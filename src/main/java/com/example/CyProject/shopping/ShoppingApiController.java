package com.example.CyProject.shopping;

import com.example.CyProject.shopping.model.item.ItemDto;
import com.example.CyProject.shopping.model.item.ItemEntity;
import com.example.CyProject.shopping.model.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping/api")
public class ShoppingApiController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/allOrderByRdtDesc")
    public List<ItemEntity> allOrderByRdtDesc(@PageableDefault(size=5) Pageable pageable) {
        return itemRepository.findAllByOrderByRdtDesc(pageable);
    }

    @GetMapping("/allOrderByRdtAsc")
    public List<ItemEntity> allOrderByRdtAsc(@PageableDefault(size=5) Pageable pageable) {
        return itemRepository.findAllByOrderByRdtAsc(pageable);
    }

    @GetMapping("/allOrderByPriceDesc")
    public List<ItemEntity> allOrderByPriceDesc(@PageableDefault(size=5) Pageable pageable) {
        return itemRepository.findAllByOrderByPriceDesc(pageable);
    }

    @GetMapping("/allOrderByPriceAsc")
    public List<ItemEntity> allOrderByPriceAsc(@PageableDefault(size=5) Pageable pageable) {
        return itemRepository.findAllByOrderByPriceAsc(pageable);
    }

    @PostMapping("/searchOrderByRdtDescAll")
    public List<ItemEntity> searchOrderByRdtDescAll(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        System.out.println(dto.getSearch());
        return itemRepository.findByNmContainsOrderByRdtDesc(dto.getSearch(), pageable);
    }

    @PostMapping("/searchOrderByRdtAscAll")
    public List<ItemEntity> searchOrderByRdtAscAll(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        return itemRepository.findByNmContainsOrderByRdtAsc(dto.getSearch(), pageable);
    }
    @PostMapping("/searchOrderByPriceDescAll")
    public List<ItemEntity> searchOrderByPriceDescAll(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        return itemRepository.findByNmContainsOrderByPriceDesc(dto.getSearch(), pageable);
    }
    @PostMapping("/searchOrderByPriceAscAll")
    public List<ItemEntity> searchOrderByPriceAscAll(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        return itemRepository.findByNmContainsOrderByPriceAsc(dto.getSearch(), pageable);
    }

    @PostMapping("/searchOrderByRdtDesc")
    public List<ItemEntity> searchOrderByRdtDesc(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(dto.getCategory(), dto.getSearch(), pageable));
        return itemRepository.searchByCategoryAndTxtByOrderByRdtDesc(dto.getCategory(), dto.getSearch(), pageable);
    }

    @PostMapping("/searchOrderByRdtAsc")
    public List<ItemEntity> searchOrderByRdtAsc(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByRdtAsc(dto.getCategory(), dto.getSearch(), pageable));
        return itemRepository.searchByCategoryAndTxtByOrderByRdtAsc(dto.getCategory(), dto.getSearch(), pageable);
    }

    @PostMapping("/searchOrderByPriceDesc")
    public List<ItemEntity> searchOrderByPriceDesc(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByPriceDesc(dto.getCategory(), dto.getSearch(), pageable));
        return itemRepository.searchByCategoryAndTxtByOrderByPriceDesc(dto.getCategory(), dto.getSearch(), pageable);
    }

    @PostMapping("/searchOrderByPriceAsc")
    public List<ItemEntity> searchOrderByPriceAsc(@RequestBody ItemDto dto, @PageableDefault(size=5) Pageable pageable) {
        System.out.println(itemRepository.searchByCategoryAndTxtByOrderByPriceAsc(dto.getCategory(), dto.getSearch(), pageable));
        return itemRepository.searchByCategoryAndTxtByOrderByPriceAsc(dto.getCategory(), dto.getSearch(), pageable);
    }
}
