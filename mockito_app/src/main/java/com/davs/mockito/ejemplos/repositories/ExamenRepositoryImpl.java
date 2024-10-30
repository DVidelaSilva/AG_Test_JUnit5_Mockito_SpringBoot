package com.davs.mockito.ejemplos.repositories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.davs.mockito.ejemplos.Datos;
import com.davs.mockito.ejemplos.models.Examen;

public class ExamenRepositoryImpl implements ExamenRepository {
    
    @Override
    public Examen guardar(Examen examen) {
        System.out.println("ExamenRepositoryImpl.guardar()");
        return Datos.EXAMEN;
    }


    @Override
    public List<Examen> findAll() {
        System.out.println("ExamenRepositoryImpl.findAll()");
        try {
            System.out.println("ExamenRepotororyOtros");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Datos.EXAMENES;
    }


}
