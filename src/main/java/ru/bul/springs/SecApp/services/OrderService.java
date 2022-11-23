package ru.bul.springs.SecApp.services;


import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bul.springs.SecApp.models.Order;
import ru.bul.springs.SecApp.models.Person;
import ru.bul.springs.SecApp.models.ShopCart;
import ru.bul.springs.SecApp.repository.OrderRepository;
import ru.bul.springs.SecApp.repository.PeopleRepository;
import ru.bul.springs.SecApp.repository.ShopCartRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final PeopleRepository peopleRepository; //???

    private final ShopCartRepository shopCartRepository;

    private final PersonService personService;





    public OrderService(OrderRepository orderRepository, PeopleRepository peopleRepository, ShopCartRepository shopCartRepository, PersonService personService) {
        this.orderRepository = orderRepository;
        this.peopleRepository = peopleRepository;
        this.shopCartRepository = shopCartRepository;
        this.personService = personService;
    }

   @Transactional
    public void addOrder(int idperson,int randomnumb){
//        personService.getPersonById(idperson); можно заменить репозиторий
        Optional<Person> per=peopleRepository.findById(idperson);
     List<ShopCart> sc= per.get().getShopCartListByPerson();
     for (var s:
             sc) {
            Order order=new Order();
            order.setNumberOfOrder(randomnumb);
            order.setCount(s.getCount());
            order.setSum(s.getSum());
            order.setPersonord(s.getPerson());
            order.setProductord(s.getProduct());
            order.setDateOfCreated(LocalDateTime.now());
            orderRepository.save(order);
            shopCartRepository.deleteById(s.getId());

        }
    }

    public List<Order> orders(){

        return orderRepository.findAll();
    }

    public List<Order> ordByNumb(String name){
        List<Order> orders=orderRepository.findAll();
        List<Order> ord=new ArrayList<>();
        for (var o:orders) {
            if(o.getPersonord().getUsername().equals(name)){
                ord.add(o);
            }
        }
        return ord;
    }

    public int allOrderSumByPersonNow(int idperson){

        Person person=personService.getPersonById(idperson).get();
        List<Order> orderList=person.getOrderListByPerson();
        int summ=0;
        for (var s:
                orderList) {
            summ=summ+s.getSum();
        }
        return summ;
    }

}
