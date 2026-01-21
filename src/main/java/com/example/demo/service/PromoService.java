package com.example.demo.service;

import com.example.demo.dto.PromoDTO;
import com.example.demo.payload.request.PromoRequest;

import java.util.List;

public interface PromoService {
    PromoDTO createPromo(PromoRequest request);
    PromoDTO updatePromo(int id, PromoRequest request);
    boolean deletePromo(int id);
    PromoDTO getPromoById(int id);
    PromoDTO getPromoByCode(String code);
    List<PromoDTO> getAllPromos();
    List<PromoDTO> getPromosByRestaurant(int resId);
    List<PromoDTO> getActivePromos();
    boolean activatePromo(int id);
    boolean deactivatePromo(int id);
}
