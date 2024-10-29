package com.davs.mockito.ejemplos.services;

import java.util.List;
import java.util.Optional;

import com.davs.mockito.ejemplos.models.Examen;
import com.davs.mockito.ejemplos.repositories.ExamenRepository;
import com.davs.mockito.ejemplos.repositories.PreguntaRepository;

public class ExamenServiceImpl implements ExamenService {
    private ExamenRepository examenRepository;
    private PreguntaRepository preguntaRepository;

    
    public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
    }


    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
       return examenRepository.findAll().stream().filter(e -> e.getNombre().contains(nombre)).findFirst();
    }


    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = null;
        if(examenOptional.isPresent()){
            examen = examenOptional.orElseThrow();
            List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }
        return examen;
    }

}
