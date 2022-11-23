package ru.bul.springs.SecApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bul.springs.SecApp.models.ShopCart;

@Repository
public interface ShopCartRepository extends JpaRepository<ShopCart,Integer> {
}
