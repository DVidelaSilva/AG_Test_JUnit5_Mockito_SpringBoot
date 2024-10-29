package com.davs.mockito.ejemplos.repositories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.davs.mockito.ejemplos.models.Examen;

public class ExamenRepositoryOtro implements ExamenRepository {

    @Override
    public List<Examen> findAll() {
        try {
            System.out.println("ExamenRepotororyOtros");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
