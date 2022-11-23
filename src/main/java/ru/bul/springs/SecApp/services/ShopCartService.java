package ru.bul.springs.SecApp.services;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.SecApp.models.Person;
import ru.bul.springs.SecApp.models.Product;
import ru.bul.springs.SecApp.models.ShopCart;
import ru.bul.springs.SecApp.repository.ShopCartRepository;
import ru.bul.springs.SecApp.secutiry.PersonDetails;

import java.util.List;

@Service
public class ShopCartService {

    private final ShopCartRepository shopCartRepository;

    private final PersonService personService;

    private final ProductService productService;


    public ShopCartService(ShopCartRepository shopCartRepository, PersonService personService, ProductService productService) {
        this.shopCartRepository = shopCartRepository;
        this.personService = personService;
        this.productService = productService;
    }

    public int messgae(int idperson,int idproduct){
        List<ShopCart> pr=personService.getPersonById(idperson).get().getShopCartListByPerson();
        Product product=productService.getProductById(idproduct).get();
        for (var sc:
            pr ) {
            if(sc.getProduct().getId()==product.getId()){
                return 1;
            }
        }
        return 0;
    }

    @Transactional
    public void addToCart(int idper,int idpro){
        Person person=personService.getPersonById(idper).get();
        Product product=productService.getProductById(idpro).get();
        ShopCart shopCart=new ShopCart();
        shopCart.setPerson(person);
        shopCart.setProduct(product);
        shopCart.setSum(product.getPrice());
        shopCart.setCount(1);
        shopCartRepository.save(shopCart);

    }

    public List<ShopCart> allProductsByPersonNow(int idperson){
        Person person=personService.getPersonById(idperson).get();
        return person.getShopCartListByPerson();

    }

    public int allProductsSumByPersonNow(int idper){

        Person person=personService.getPersonById(idper).get();
        List<ShopCart> shopCartList=person.getShopCartListByPerson();
       int summ=0;
        for (var s:
             shopCartList) {
            summ=summ+s.getSum();
        }
        return summ;
    }

    @Transactional
    public void plusToCart(int idperson,int idproduct){
        List<ShopCart> shopCart=  allProductsByPersonNow(idperson);
        for (var pr:
             shopCart) {
           if (pr.getId()==idproduct){
               pr.setCount(pr.getCount()+1);
               pr.setSum(pr.getProduct().getPrice()*pr.getCount());
           }
        }
    }
    @Transactional
    public void minusToCart(int idperson,int idproduct){
        List<ShopCart> shopCart=  allProductsByPersonNow(idperson);
        for (var pr:
                shopCart) {
            if (pr.getId()==idproduct){
                pr.setCount(pr.getCount()-1);
                pr.setSum(pr.getProduct().getPrice()*pr.getCount());

                if(pr.getCount()==0){
                    shopCartRepository.deleteById(idproduct);
                }
            }
        }
    }

    @Transactional
    public void deleteFromCart(int idproduct){
        shopCartRepository.deleteById(idproduct);
    }
}
