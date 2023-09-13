package com.microservices.example.inventoryservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="torder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Torder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="order_number", unique = true)
    private String orderNumber;
    @OneToMany(mappedBy="torder", cascade=CascadeType.ALL)
    private List<OrderItem> orderItemList;
}
