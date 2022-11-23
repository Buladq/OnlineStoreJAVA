package ru.bul.springs.SecApp.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bul.springs.SecApp.models.Product;
import ru.bul.springs.SecApp.secutiry.PersonDetails;
import ru.bul.springs.SecApp.services.PersonService;
import ru.bul.springs.SecApp.services.ProductService;
import ru.bul.springs.SecApp.services.ShopCartService;

@Controller
@RequestMapping("/product")//действия покупателя
public class ProductController {
    private final ProductService productService;

    private final PersonService personService;

    private final ShopCartService shopCartService;

    public ProductController(ProductService productService, PersonService personService, ShopCartService shopCartService) {
        this.productService = productService;
        this.personService = personService;
        this.shopCartService = shopCartService;
    }

    @GetMapping
    public String allProducts(Model model,@RequestParam(value = "titleser" ,required = false) String titleser ,
                              @ModelAttribute("product")Product product,
                              @RequestParam(value = "expensive" ,required = false) boolean expensive,
                              @RequestParam(value = "cheap" ,required = false) boolean cheap){

        String nameUser = SecurityContextHolder.getContext().getAuthentication().getName();

//
//        if(personService.getUserByUsername(nameUser).isPresent()){
//            model.addAttribute("addBtn","addBtn"); //Добавляю функционал для авторизованныз пользователей
//            if(personService.getUserByUsername(nameUser).get().getRole().equals("ROLE_ADMIN")){ //добавляю функционал для админа
//                model.addAttribute("btnforadmin","btnforadmin");
//            }
//        }

        if(titleser==null||titleser.equals("") ){
            model.addAttribute("all","all");
            model.addAttribute("products",productService.findAllProducts(expensive,cheap));
        }
        else if(productService.findByTitle(titleser).size()==0  || productService.findAllProducts(expensive,cheap).size()==0){
            model.addAttribute("nothing","Товара нет");
        }

        else {
            model.addAttribute("thereis","thereis");
            model.addAttribute("productsfind",productService.findByTitle(titleser));
        }


        return "product/allproducts";
    }

    @GetMapping("/{id}")
    public String productInfo(@PathVariable("id")int id,Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        Product productim=productService.getProductById(id).get();
        model.addAttribute("photo",productim.getImages());
        model.addAttribute("product",productService.getProductById(id).get());
        if (personDetails.getPerson().getRole().equals("ROLE_ADMIN")){
            model.addAttribute("btnforadmin","btnforadmin");
        }
        if (shopCartService.messgae(personDetails.getPerson().getId(),id)==0){
            model.addAttribute("inNonCart","Товара нет корзине");
        }
        if(shopCartService.messgae(personDetails.getPerson().getId(),id)!=0){
            model.addAttribute("inCart","В корзине");
        }


        return "product/info";
    }

    @PostMapping("/addToCart/{id}")
    public String addToShop(@PathVariable("id")int id,Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails =   (PersonDetails) authentication.getPrincipal();
        shopCartService.addToCart(personDetails.getPerson().getId(),productService.getProductById(id).get().getId());
        return "redirect:/product/"+id;
    }







}
