package ru.bul.springs.SecApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bul.springs.SecApp.models.Product;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findBytitle(String title);
}
