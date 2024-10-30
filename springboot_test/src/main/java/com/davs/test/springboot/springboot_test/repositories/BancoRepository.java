package com.davs.test.springboot.springboot_test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davs.test.springboot.springboot_test.models.Banco;

public interface BancoRepository extends JpaRepository<Banco, Long>{
    
    // List<Banco> findAll();
    // Banco findById(Long id);
    // void update(Banco banco);

    
    
}
