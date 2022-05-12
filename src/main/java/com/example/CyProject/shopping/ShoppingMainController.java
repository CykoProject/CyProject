package com.example.CyProject.shopping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingMainController {

    @GetMapping("/shopping")
    public String shoppingMain() {

        return "shopping/shoppingMain";
    }
}
