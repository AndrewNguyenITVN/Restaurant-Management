package com.example.demo.repository;

import com.example.demo.entity.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromoRepository extends JpaRepository<Promo, Integer> {
    Optional<Promo> findByCode(String code);
    List<Promo> findByResIdId(int resId);
    List<Promo> findByIsActiveTrue();
}
