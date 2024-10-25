package com.davs.junit5.ejemplos.exceptions;

public class DineroInsuficienteException extends RuntimeException{

    public DineroInsuficienteException(String message) {
        super(message);
    }

}
