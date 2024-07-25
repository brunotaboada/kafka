package com.example.demo.service;

import com.example.demo.model.Fee;
import com.example.demo.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    public Fee feeUser(Fee user) {
        return feeRepository.save(user);
    }
}