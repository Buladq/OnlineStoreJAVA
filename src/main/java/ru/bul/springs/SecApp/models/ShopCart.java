package ru.bul.springs.SecApp.models;


import javax.persistence.*;

@Entity
@Table(name = "ShopCart")
public class ShopCart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sum")
    private int sum;

    @ManyToOne
    @JoinColumn(name = "idproduct",referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idperson",referencedColumnName = "id")
    private Person person;

    @Column(name = "count")
    private int count;



    public ShopCart() {
    }

    public ShopCart(int sum, Product product, Person person) {
        this.sum = sum;
        this.product = product;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
