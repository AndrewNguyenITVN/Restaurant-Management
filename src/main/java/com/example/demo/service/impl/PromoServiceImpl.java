package com.example.demo.service.impl;

import com.example.demo.dto.PromoDTO;
import com.example.demo.entity.Promo;
import com.example.demo.entity.Restaurant;
import com.example.demo.payload.request.PromoRequest;
import com.example.demo.repository.PromoRepository;
import com.example.demo.repository.RestaurantRepository;
import com.example.demo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoRepository promoRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public PromoDTO createPromo(PromoRequest request) {
        try {
            Optional<Restaurant> restaurant = restaurantRepository.findById(request.getResId());
            if (restaurant.isEmpty()) {
                return null;
            }

            Promo promo = new Promo();
            promo.setCode(request.getCode().toUpperCase());
            promo.setDescription(request.getDescription());
            promo.setResId(restaurant.get());
            promo.setDiscountType(request.getDiscountType());
            promo.setDiscountValue(request.getDiscountValue());
            promo.setMinOrderValue(request.getMinOrderValue());
            promo.setMaxDiscount(request.getMaxDiscount());
            promo.setUsageLimit(request.getUsageLimit());
            promo.setUsedCount(0);
            promo.setStartDate(request.getStartDate());
            promo.setEndDate(request.getEndDate());
            promo.setActive(true);

            Promo saved = promoRepository.save(promo);
            return convertToDTO(saved);
        } catch (Exception e) {
            System.out.println("Error creating promo: " + e.getMessage());
            return null;
        }
    }

    @Override
    public PromoDTO updatePromo(int id, PromoRequest request) {
        try {
            Optional<Promo> existingPromo = promoRepository.findById(id);
            if (existingPromo.isEmpty()) {
                return null;
            }

            Promo promo = existingPromo.get();
            promo.setDescription(request.getDescription());
            promo.setDiscountType(request.getDiscountType());
            promo.setDiscountValue(request.getDiscountValue());
            promo.setMinOrderValue(request.getMinOrderValue());
            promo.setMaxDiscount(request.getMaxDiscount());
            promo.setUsageLimit(request.getUsageLimit());
            promo.setStartDate(request.getStartDate());
            promo.setEndDate(request.getEndDate());

            Promo updated = promoRepository.save(promo);
            return convertToDTO(updated);
        } catch (Exception e) {
            System.out.println("Error updating promo: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deletePromo(int id) {
        try {
            if (promoRepository.existsById(id)) {
                promoRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error deleting promo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public PromoDTO getPromoById(int id) {
        Optional<Promo> promo = promoRepository.findById(id);
        return promo.map(this::convertToDTO).orElse(null);
    }

    @Override
    public PromoDTO getPromoByCode(String code) {
        Optional<Promo> promo = promoRepository.findByCode(code.toUpperCase());
        return promo.map(this::convertToDTO).orElse(null);
    }

    @Override
    public List<PromoDTO> getAllPromos() {
        List<Promo> promos = promoRepository.findAll();
        List<PromoDTO> promoDTOs = new ArrayList<>();
        for (Promo promo : promos) {
            promoDTOs.add(convertToDTO(promo));
        }
        return promoDTOs;
    }

    @Override
    public List<PromoDTO> getPromosByRestaurant(int resId) {
        List<Promo> promos = promoRepository.findByResIdId(resId);
        List<PromoDTO> promoDTOs = new ArrayList<>();
        for (Promo promo : promos) {
            promoDTOs.add(convertToDTO(promo));
        }
        return promoDTOs;
    }

    @Override
    public List<PromoDTO> getActivePromos() {
        List<Promo> promos = promoRepository.findByIsActiveTrue();
        List<PromoDTO> promoDTOs = new ArrayList<>();
        for (Promo promo : promos) {
            promoDTOs.add(convertToDTO(promo));
        }
        return promoDTOs;
    }

    @Override
    public boolean activatePromo(int id) {
        try {
            Optional<Promo> promo = promoRepository.findById(id);
            if (promo.isPresent()) {
                Promo p = promo.get();
                p.setActive(true);
                promoRepository.save(p);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error activating promo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deactivatePromo(int id) {
        try {
            Optional<Promo> promo = promoRepository.findById(id);
            if (promo.isPresent()) {
                Promo p = promo.get();
                p.setActive(false);
                promoRepository.save(p);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error deactivating promo: " + e.getMessage());
            return false;
        }
    }

    private PromoDTO convertToDTO(Promo promo) {
        PromoDTO dto = new PromoDTO();
        dto.setId(promo.getId());
        dto.setCode(promo.getCode());
        dto.setDescription(promo.getDescription());
        dto.setResId(promo.getResId().getId());
        dto.setRestaurantName(promo.getResId().getTitle());
        dto.setDiscountType(promo.getDiscountType());
        dto.setDiscountValue(promo.getDiscountValue());
        dto.setMinOrderValue(promo.getMinOrderValue());
        dto.setMaxDiscount(promo.getMaxDiscount());
        dto.setUsageLimit(promo.getUsageLimit());
        dto.setUsedCount(promo.getUsedCount());
        dto.setStartDate(promo.getStartDate());
        dto.setEndDate(promo.getEndDate());
        dto.setActive(promo.isActive());
        return dto;
    }
}
