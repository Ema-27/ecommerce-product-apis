package com.educative.ecommerce.controllers;

import com.educative.ecommerce.dto.product.PurchaseDto;
import com.educative.ecommerce.model.Purchase;
import com.educative.ecommerce.model.User;
import com.educative.ecommerce.service.PurchaseService;
import com.educative.ecommerce.service.UserService;
import com.educative.ecommerce.support.DateWrongRangeException;
import com.educative.ecommerce.support.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity createPurchase(@RequestBody @Valid PurchaseDto purchaseDto){
        Optional<User> optionalUser = Optional.ofNullable(userService.readUser(purchaseDto.getBuyerId()));
        if (!optionalUser.isPresent()) {
            return new ResponseEntity("user is invalid", HttpStatus.CONFLICT);
        }
        ArrayList<Integer> tmp= new ArrayList<>(0);
        for(int i=0; i<purchaseDto.getProva().size(); i++){
            if(!tmp.contains(purchaseDto.getProva().get(i)))
                tmp.add(purchaseDto.getProva().get(i));
            else
                return new ResponseEntity("check the product list, there are 2 elements with the same id", HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();
        try{
            purchaseService.addPurchase(purchaseDto, user);
            return new ResponseEntity("created purchase", HttpStatus.CREATED);
        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product quantity unavailable!");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Purchase>> getPurchases(){
        List<Purchase> body = purchaseService.listPurchases();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public List<Purchase> listPurchases(@PathVariable("userId") @RequestBody @Valid int userId){
        User user= userService.readUser(userId);
        try{
            return purchaseService.listPurchasesByUser(user);
        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found!");
        }
    }

    @GetMapping("/{userId}/{dataI}/{dataF}")
    public ResponseEntity listPurchasesInPeriod( @PathVariable("userId") @RequestBody @Valid int userId,
                                                 @PathVariable("dataI") @DateTimeFormat(pattern = "dd-MM-yyyy")Date dataI,
                                                 @PathVariable("dataF") @DateTimeFormat(pattern = "dd-MM-yyyy")Date dataF){
        User user= userService.readUser(userId);
        try{
            List<Purchase> result = purchaseService.listPurchasesByUserInPeriod(user, dataI, dataF);
            if(result.size()<=0)
                return new ResponseEntity("No result", HttpStatus.OK);
            return new ResponseEntity(result, HttpStatus.CREATED);
        }catch (UserNotFoundException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User non found!");
        }catch (DateWrongRangeException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "check start date and end date");
        }
    }

}
