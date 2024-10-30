package com.davs.mockito.ejemplos.services;

import java.util.Optional;

import com.davs.mockito.ejemplos.models.Examen;

public interface ExamenService {

    Optional<Examen> findExamenPorNombre(String nombre);

    Examen findExamenPorNombreConPreguntas(String nombre);
    Examen guardar(Examen examen);

}
