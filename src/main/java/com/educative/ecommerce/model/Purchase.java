package com.educative.ecommerce.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private @NotNull double price;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private @NotNull Date purchaseTime;

    @ManyToOne
    @JoinColumn(name = "buyer")
    private User buyer;


    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private List<ProductInPurchase> productsInPurchaseList;

}
