package dev.acobano.miprimerproyectoconkeycloak.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Builder
public class ClienteDTO
{
    private String idCliente;
    private String nombre;
    private String descripcion;
    private String urlRaiz;
    private String urlAdmin;
    private String urlBase;
    private boolean estaActivo;
    private String clientSecret;
}
