package com.educative.ecommerce.controllers;

import com.educative.ecommerce.model.User;
import com.educative.ecommerce.service.UserService;
import com.educative.ecommerce.support.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity createUser(@Valid @RequestBody User user){
        try{
            User adder=userService.registerUser(user);
            return new ResponseEntity("created user", HttpStatus.CREATED);
        }catch (MailUserAlreadyExistsException e){
            return new ResponseEntity<>("user already exist", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        List<User> body = userService.listUsers();
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
