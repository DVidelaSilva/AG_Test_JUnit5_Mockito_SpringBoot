package com.davs.mockito.ejemplos.repositories;

import java.util.List;

import com.davs.mockito.ejemplos.models.Examen;

public interface ExamenRepository {

    Examen guardar(Examen examen);
    List<Examen> findAll();

}
