package com.example.demo.controller;

import com.example.demo.dto.PromoDTO;
import com.example.demo.payload.ResponseData;
import com.example.demo.payload.request.PromoRequest;
import com.example.demo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promo")
public class PromoController {

    @Autowired
    private PromoService promoService;

    @PostMapping
    public ResponseEntity<?> createPromo(@RequestBody PromoRequest request) {
        ResponseData responseData = new ResponseData();
        PromoDTO promo = promoService.createPromo(request);
        
        if (promo != null) {
            responseData.setSuccess(true);
            responseData.setData(promo);
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        } else {
            responseData.setSuccess(false);
            responseData.setData("Failed to create promo");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromo(@PathVariable int id, @RequestBody PromoRequest request) {
        ResponseData responseData = new ResponseData();
        PromoDTO promo = promoService.updatePromo(id, request);
        
        if (promo != null) {
            responseData.setSuccess(true);
            responseData.setData(promo);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setSuccess(false);
            responseData.setData("Promo not found");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromo(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        boolean deleted = promoService.deletePromo(id);
        
        responseData.setSuccess(deleted);
        responseData.setData(deleted ? "Promo deleted successfully" : "Failed to delete promo");
        return new ResponseEntity<>(responseData, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPromoById(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        PromoDTO promo = promoService.getPromoById(id);
        
        if (promo != null) {
            responseData.setSuccess(true);
            responseData.setData(promo);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setSuccess(false);
            responseData.setData("Promo not found");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getPromoByCode(@PathVariable String code) {
        ResponseData responseData = new ResponseData();
        PromoDTO promo = promoService.getPromoByCode(code);
        
        if (promo != null) {
            responseData.setSuccess(true);
            responseData.setData(promo);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setSuccess(false);
            responseData.setData("Promo code not found");
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPromos() {
        ResponseData responseData = new ResponseData();
        List<PromoDTO> promos = promoService.getAllPromos();
        
        responseData.setSuccess(true);
        responseData.setData(promos);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{resId}")
    public ResponseEntity<?> getPromosByRestaurant(@PathVariable int resId) {
        ResponseData responseData = new ResponseData();
        List<PromoDTO> promos = promoService.getPromosByRestaurant(resId);
        
        responseData.setSuccess(true);
        responseData.setData(promos);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActivePromos() {
        ResponseData responseData = new ResponseData();
        List<PromoDTO> promos = promoService.getActivePromos();
        
        responseData.setSuccess(true);
        responseData.setData(promos);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activatePromo(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        boolean activated = promoService.activatePromo(id);
        
        responseData.setSuccess(activated);
        responseData.setData(activated ? "Promo activated" : "Failed to activate promo");
        return new ResponseEntity<>(responseData, activated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivatePromo(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        boolean deactivated = promoService.deactivatePromo(id);
        
        responseData.setSuccess(deactivated);
        responseData.setData(deactivated ? "Promo deactivated" : "Failed to deactivate promo");
        return new ResponseEntity<>(responseData, deactivated ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
