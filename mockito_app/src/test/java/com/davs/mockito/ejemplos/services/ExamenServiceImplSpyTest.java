package com.davs.mockito.ejemplos.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

import com.davs.mockito.ejemplos.models.Examen;
import com.davs.mockito.ejemplos.repositories.ExamenRepositoryImpl;
import com.davs.mockito.ejemplos.repositories.PreguntaRepositoryImpl;


@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplSpyTest {

    @Spy
    ExamenRepositoryImpl repository;

    @Spy
    PreguntaRepositoryImpl preguntaRepository;
    
    @InjectMocks
    ExamenServiceImpl service;



    @Test
    void testSpy() {


        List<String> preguntas = Arrays.asList("aritmética");
        //when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(preguntas);

        doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");
        assertEquals(5, examen.getId());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmética"));

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());

    }

    



}
