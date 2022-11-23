package ru.bul.springs.SecApp.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.SecApp.secutiry.PersonDetails;
import ru.bul.springs.SecApp.services.OrderService;
import ru.bul.springs.SecApp.services.PersonService;
import ru.bul.springs.SecApp.services.ShopCartService;

import java.util.Random;

@Controller
@RequestMapping("/carts")
public class ShopCartController {

    private final ShopCartService shopCartService;

    private final PersonService personService;

    private final OrderService orderService;

    public ShopCartController(ShopCartService shopCartService, PersonService personService, OrderService orderService) {
        this.shopCartService = shopCartService;
        this.personService = personService;
        this.orderService = orderService;
    }


    @GetMapping()
    public String cartAll(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        int idn=personDetails.getPerson().getId();
        if(shopCartService.allProductsSumByPersonNow(idn)==0){
            model.addAttribute("emptys","emptys");
        }
        else {
            model.addAttribute("notnull","notnull");
            model.addAttribute("cart",shopCartService.allProductsByPersonNow(idn));
            model.addAttribute("sum",shopCartService.allProductsSumByPersonNow(idn));
        }




        return "shopCart/carts";
    }

    @PatchMapping("/plusToCart/{id}")
    public String addTo(@PathVariable("id")int idprod){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        shopCartService.plusToCart(personDetails.getPerson().getId(),idprod);
        return "redirect:/carts";
    }

    @PatchMapping("/minusToCart/{id}")
    public String delTo(@PathVariable("id")int idprod){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();

        shopCartService.minusToCart(personDetails.getPerson().getId(),idprod);
        return "redirect:/carts";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable("id")int idPr){
        shopCartService.deleteFromCart(idPr);
        return "redirect:/carts";
    }


    @PostMapping("/newOrder")
    public String order(Model model){
        int min = 100;
        int max = 200;
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += min;

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        orderService.addOrder(personDetails.getPerson().getId(),i);
        model.addAttribute("order",i);

        return "order/sucess";

    }


}
