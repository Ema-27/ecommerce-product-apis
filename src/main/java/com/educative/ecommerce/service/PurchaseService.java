package com.educative.ecommerce.service;

import com.educative.ecommerce.dto.product.PurchaseDto;
import com.educative.ecommerce.model.*;
import com.educative.ecommerce.repository.ProductInPurchaseRepository;
import com.educative.ecommerce.repository.ProductRepository;
import com.educative.ecommerce.repository.PurchaseRepository;
import com.educative.ecommerce.repository.UserRepository;
import com.educative.ecommerce.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;

    @Autowired
     ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    public  Purchase getPurchaseFromDto(PurchaseDto purchaseDto, User user){
        Purchase purchase= new Purchase();
        purchase.setBuyer(user);
        purchase.setPurchaseTime(purchaseDto.getPurchaseTime());
        double p=0;
        for(Integer i : purchaseDto.getProva()) {
            p += productRepository.getById(i).getPrice();
        }
        purchase.setPrice(p);
        return purchase;
    }

    @Transactional(readOnly = false)
    public Purchase addPurchase(PurchaseDto purchasedto, User user)throws UserNotFoundException{
        Purchase p = getPurchaseFromDto(purchasedto, user);
        Purchase result = purchaseRepository.save(p);
        for(Integer i : purchasedto.getProva()) {
            if(!productRepository.existsById(i))
                throw new UserNotFoundException();
        }
        entityManager.refresh(result);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Purchase> listPurchases(){

        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Purchase> listPurchasesByUser(User user) throws UserNotFoundException {
        if(!userRepository.existsById(user.getId()))
            throw new UserNotFoundException();
        return purchaseRepository.findByBuyer(user);
    }

    @Transactional(readOnly = true)
    public List<Purchase> listPurchasesByUserInPeriod(User user, Date dataI, Date dataF) throws UserNotFoundException, DateWrongRangeException {
        if(!userRepository.existsById(user.getId()))
            throw new UserNotFoundException();
        if(dataI.compareTo(dataF)>=0)
            throw new DateWrongRangeException();
        return purchaseRepository.findByBuyerInPeriod(dataI,dataF,user);
    }

    @Transactional(readOnly = true)
    public Optional<User> readUser(Integer userId) {
        return userRepository.findById(userId);
    }

}
