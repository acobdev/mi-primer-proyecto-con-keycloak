package dev.acobano.miprimerproyectoconkeycloak.controladores;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MiControlador
{
    @GetMapping("/encargado")
    @PreAuthorize("hasRole('ENCARGADO-rol-cliente')")
    public String endpointEncargado()
    {
        return "Si estás leyendo esto, es porque has accedido a este endpoint con un usuario con rol de ENCARGADO.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR-rol-cliente')")
    public String endpointAdmin()
    {
        return "Si estás leyendo esto, es porque has accedido a este endpoint con un usuario con rol de ADMINISTRADOR.";
    }

    @GetMapping("/combinado")
    @PreAuthorize("hasRole('ENCARGADO-rol-cliente') or hasRole('ADMINISTRADOR-rol-cliente')")
    public String endpointCombo()
    {
        return "Si estás leyendo esto, es porque has accedido a este endpoint con un usuario con rol de ENCARGADO" +
                "o con rol de ADMINISTRADOR.";
    }
}
