package ru.bul.springs.SecApp.models;


import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "title")
    private String title;
    @NotNull
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @NotNull
    @Min(value = 0)
    @Column(name = "price")
    private int price;
    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "productid")
    private List<Image> images=new ArrayList<>();

    @Column(name = "preview_image_id")
    private Long previewImageId;

    @OneToMany(mappedBy = "product")
    private List<ShopCart> shopCartListByProduct;

    @OneToMany(mappedBy = "productord")
    private List<Order> orderListByProduct;







    public Product() {
    }

    public Product(String title, String description, int price, String city, List<Image> images, Long previewImageId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.images = images;
        this.previewImageId = previewImageId;
    }

    public void addImageToProduct(Image image) {
        image.setProductid(this);
        images.add(image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Long getPreviewImageId() {
        return previewImageId;
    }

    public void setPreviewImageId(Long previewImageId) {
        this.previewImageId = previewImageId;
    }

    public List<ShopCart> getShopCartListByProduct() {
        return shopCartListByProduct;
    }

    public void setShopCartListByProduct(List<ShopCart> shopCartListByProduct) {
        this.shopCartListByProduct = shopCartListByProduct;
    }

    public List<Order> getOrderListByProduct() {
        return orderListByProduct;
    }

    public void setOrderListByProduct(List<Order> orderListByProduct) {
        this.orderListByProduct = orderListByProduct;
    }
}