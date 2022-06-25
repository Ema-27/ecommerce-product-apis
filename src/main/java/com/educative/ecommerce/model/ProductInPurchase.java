package com.educative.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "product_in_purchase")
public class ProductInPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private @NotNull int quantity;

    private @NotNull double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private @NotNull Purchase purchase;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product")
    private Product product;
}
