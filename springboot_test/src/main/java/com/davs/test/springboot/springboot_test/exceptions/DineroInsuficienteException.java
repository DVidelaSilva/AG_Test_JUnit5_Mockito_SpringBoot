package com.davs.test.springboot.springboot_test.exceptions;

public class DineroInsuficienteException  extends RuntimeException {
    public DineroInsuficienteException(String message) {
        super(message);
    }
}
