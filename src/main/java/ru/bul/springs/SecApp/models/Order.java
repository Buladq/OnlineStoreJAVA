package ru.bul.springs.SecApp.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Order_all")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "numbeorder")
    private int numberOfOrder;

    @Column(name = "datecreated")
    private LocalDateTime dateOfCreated;

    @Column(name = "sum")
    private int sum;

    @ManyToOne
    @JoinColumn(name = "id_product",referencedColumnName = "id")
    private Product productord;

    @ManyToOne
    @JoinColumn(name = "id_person",referencedColumnName = "id")
    private Person personord;

    @Column(name = "countproduct")
    private int count;

    public Order(int numberOfOrder, LocalDateTime dateOfCreated, int sum, Product productord, Person personord, int count) {
        this.numberOfOrder = numberOfOrder;
        this.dateOfCreated = dateOfCreated;
        this.sum = sum;
        this.productord = productord;
        this.personord = personord;
        this.count = count;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Product getProductord() {
        return productord;
    }

    public void setProductord(Product productord) {
        this.productord = productord;
    }

    public Person getPersonord() {
        return personord;
    }

    public void setPersonord(Person personord) {
        this.personord = personord;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
