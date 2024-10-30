package com.davs.test.springboot.springboot_test;

import java.math.BigDecimal;
import java.util.Optional;

import com.davs.test.springboot.springboot_test.models.Banco;
import com.davs.test.springboot.springboot_test.models.Cuenta;

public class Datos {

    public static Optional<Cuenta> crearCuenta001(){
        return Optional.of(new Cuenta(1L, "Andrés", new BigDecimal("1000")));
    };

    public static Optional<Cuenta> crearCuenta002(){
        return Optional.of(new Cuenta(2L, "Jhon", new BigDecimal("2000")));
    }

    public static Optional<Banco> crearBanco(){
        return Optional.of(new Banco(1L, "El banco financiero", 0));
    }
}
