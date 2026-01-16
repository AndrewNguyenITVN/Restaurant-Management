package com.example.demo.entity;

import com.example.demo.entity.keys.KeyOrderItem;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "order_item")
public class OrderItem {
    @EmbeddedId
    KeyOrderItem KeyOrderItem;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Orders orderId;

    @ManyToOne
    @JoinColumn(name = "food_id", insertable = false, updatable = false)
    private Food foodId;

    @Column(name = "create_date")
    private Date createDate;

    public com.example.demo.entity.keys.KeyOrderItem getKeyOrderItem() {
        return KeyOrderItem;
    }

    public void setKeyOrderItem(com.example.demo.entity.keys.KeyOrderItem KeyOrderItem) {
        this.KeyOrderItem = KeyOrderItem;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Food getFoodId() {
        return foodId;
    }

    public void setFoodId(Food foodId) {
        this.foodId = foodId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
