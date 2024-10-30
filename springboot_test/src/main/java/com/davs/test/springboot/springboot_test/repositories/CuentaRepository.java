package com.davs.test.springboot.springboot_test.repositories;

import java.util.List;

import com.davs.test.springboot.springboot_test.models.Cuenta;

public interface CuentaRepository {

    List<Cuenta> findAll();
    Cuenta findById(Long id);

    void update(Cuenta cuenta);
    

}
