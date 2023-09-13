package com.microservices.example.orderservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="product_code")
    private String productCode;
    @Column(name="quantity")
    private int quantity;
    @Column(name="unit_price")
    private BigDecimal unitPrice;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="torder_id", nullable = false)
    private Torder torder;
}
