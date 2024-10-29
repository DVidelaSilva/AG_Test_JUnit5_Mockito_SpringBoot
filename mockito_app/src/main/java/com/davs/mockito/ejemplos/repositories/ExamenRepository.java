package com.davs.mockito.ejemplos.repositories;

import java.util.List;

import com.davs.mockito.ejemplos.models.Examen;

public interface ExamenRepository {

    List<Examen> findAll();

}
