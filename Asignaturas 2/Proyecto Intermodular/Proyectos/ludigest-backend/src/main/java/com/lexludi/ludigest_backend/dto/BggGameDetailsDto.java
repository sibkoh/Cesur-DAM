package com.lexludi.ludigest_backend.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BggGameDetailsDto {
    private Long bggId;
    private String titulo;
    private Integer anoPublicacion;
    private String urlImagen;
    private Integer minJugadores;
    private Integer maxJugadores;
    private Integer duracionMinutos;
    private Double dureza;
    private String descripcion;
    private List<String> categorias = new ArrayList<>();
    private List<String> mecanicas = new ArrayList<>();
}