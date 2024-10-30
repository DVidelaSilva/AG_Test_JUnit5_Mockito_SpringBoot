package com.davs.test.springboot.springboot_test.repositories;

import java.util.List;

import com.davs.test.springboot.springboot_test.models.Banco;

public interface BancoRepository {
    
    List<Banco> findAll();
    Banco findById(Long id);

    void update(Banco banco);
    
}
