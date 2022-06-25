package com.educative.ecommerce.dto.product;

import com.educative.ecommerce.model.ProductInPurchase;
import com.educative.ecommerce.model.Purchase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PurchaseDto {

    private Integer id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private @NotNull Date purchaseTime;

    private @NotNull Integer buyerId;

    private @NotNull List<Integer> prova;//lista id prodotti ordine

    public PurchaseDto(Purchase purchase){
        this.setId(purchase.getId());
        this.setPurchaseTime(purchase.getPurchaseTime());
        this.setBuyerId(purchase.getBuyer().getId());
        for(ProductInPurchase pip: purchase.getProductsInPurchaseList())
            prova.add(pip.getProduct().getId());

    }


    public PurchaseDto(){

    }
}
