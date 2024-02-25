package dev.acobano.miprimerproyectoconkeycloak.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class RolDTO
{
    private String nombre;
    private String descripcion;
}
