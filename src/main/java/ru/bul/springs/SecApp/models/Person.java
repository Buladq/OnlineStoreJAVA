package ru.bul.springs.SecApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 2, max = 60, message = "Значение от 2 до 60 симсволов")
    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;


    @NotEmpty
    @Size(min = 2, max = 60, message = "Значение от 2 до 60 симсволов")
    @Column(name = "city")
    private String city;


    @Column(name = "role")
    private String role;


    @NotNull
    @Column(name = "phoneNumber")
    private String phoneNumber;


    @Column(name = "activite")
    private boolean activite;

    @OneToOne()
    @JoinColumn(name = "avatar_id",referencedColumnName = "id")
    private Image avatar_id;



    @OneToMany(mappedBy = "person")
    private List<ShopCart> shopCartListByPerson;


    @OneToMany(mappedBy = "personord")
    private List<Order> orderListByPerson;




    public Person() {
    }

    public Person(String username, String city, String role, String phoneNumber, boolean activite) {
        this.username = username;
        this.city = city;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.activite = activite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActivite() {
        return activite;
    }

    public void setActivite(boolean activite) {
        this.activite = activite;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Image getAvatar_id() {
        return avatar_id;
    }

    public void setAvatar_id(Image avatar_id) {
        this.avatar_id = avatar_id;
    }

    public List<ShopCart> getShopCartListByPerson() {
        return shopCartListByPerson;
    }

    public void setShopCartListByPerson(List<ShopCart> shopCartList) {
        this.shopCartListByPerson = shopCartList;
    }

    public List<Order> getOrderListByPerson() {
        return orderListByPerson;
    }

    public void setOrderListByPerson(List<Order> orderListByPerson) {
        this.orderListByPerson = orderListByPerson;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", role='" + role + '\'' +
                ", activite=" + activite +
                '}';
    }
}