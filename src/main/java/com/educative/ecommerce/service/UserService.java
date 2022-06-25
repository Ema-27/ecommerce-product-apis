package com.educative.ecommerce.service;

import com.educative.ecommerce.model.User;
import com.educative.ecommerce.repository.UserRepository;
import com.educative.ecommerce.support.MailUserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = false)
    public User registerUser(User user) throws MailUserAlreadyExistsException {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new MailUserAlreadyExistsException();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers(){

        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User readUser(int id) {
        return userRepository.findById(id);
    }

}
