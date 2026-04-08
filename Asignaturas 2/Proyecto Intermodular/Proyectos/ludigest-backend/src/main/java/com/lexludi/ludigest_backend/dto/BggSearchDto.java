package com.lexludi.ludigest_backend.dto;

import lombok.Data;

@Data
public class BggSearchDto {
    private Long bggId;
    private String titulo;
    private Integer anoPublicacion;
}