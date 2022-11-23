package ru.bul.springs.SecApp.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bul.springs.SecApp.models.Product;
import ru.bul.springs.SecApp.services.OrderService;
import ru.bul.springs.SecApp.services.PersonService;
import ru.bul.springs.SecApp.services.ProductService;


@Controller
@RequestMapping("/admin")
public class AdminContorller {
    private final PersonService personService;

    private final OrderService orderService;
    private final ProductService productService;

    public AdminContorller(PersonService personService, OrderService orderService, ProductService productService) {
        this.personService = personService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/showADmin")
    public String admin(Model model){
        model.addAttribute("users",personService.findAll());
        return "admin/admin";
    }

    @PatchMapping("/ban/{id}")
    public String banUser(@PathVariable("id") int id) {
       personService.banUser(id);
        return "redirect:/admin/showADmin";
    }

    @GetMapping("/showUser/{id}")
    public String admin(Model model,@PathVariable("id") int id){
        if(personService.getPersonById(id).get().getAvatar_id() != null){
            model.addAttribute("image","image");
        }
        model.addAttribute("user",personService.getPersonById(id).get());
        return "admin/infouser";
    }


    @GetMapping("/create")
    public String allProducts(@ModelAttribute("product")Product product){
        return "admin/createProduct";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") Product product, @RequestParam(value = "file1",required = false) MultipartFile file1,
                         @RequestParam(value = "file2",required = false) MultipartFile file2,
                         @RequestParam("file3") MultipartFile file3, BindingResult bindResult) throws Exception {
        if(bindResult.hasErrors()){
            return "admin/createProduct";
        }
        productService.saveProduct(product,file1,file2,file3);
        return "redirect:/product";
    }

    @DeleteMapping("/product/delete/{id}")
    public String delete(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/product";
    }




    @GetMapping("/product/change/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", productService.getProductById(id).get());
        return "product/edit";
    }

    @PatchMapping("/product/change/{id}")
    public String update(@ModelAttribute("product")  Product product,BindingResult bindingResult
            , @PathVariable("id") int id, @RequestParam(value = "file1",required = false) MultipartFile file1,
                         @RequestParam(value = "file2",required = false) MultipartFile file2,
                         @RequestParam("file3") MultipartFile file3, BindingResult bindResult,
                         Model model) throws Exception {


        if(bindingResult.hasErrors()){
            return "product/edit";
        }
        if(file1.getSize()==0|| file2.getSize()==0 ||file3.getSize()==0){
            model.addAttribute("error","error");
            return "product/edit";
        }

        productService.updateProduct(id,product,file1,file2,file3);
        return "redirect:/product";
    }

    @GetMapping("/orders")
    public String allOrders(Model model,@RequestParam(value = "numb" ,required = false) String numb){
        if(numb!=null && !numb.equals("") && !numb.equals(" ")){
            if(orderService.ordByNumb(numb).size()!=0){
                model.addAttribute("theare","theare");
                model.addAttribute("find",orderService.ordByNumb(numb));
            }
        }
        else {
            model.addAttribute("all","all");
            model.addAttribute("allOrders",orderService.orders());
        }





        return "admin/orders";
    }


}
