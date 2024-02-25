package dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces;

import org.keycloak.representations.idm.UserRepresentation;
import dev.acobano.miprimerproyectoconkeycloak.dto.UsuarioDTO;
import java.util.List;

public interface IServicioUsuarios
{
    List<UserRepresentation> listarUsuarios();
    List<UserRepresentation> buscarUsuarioPorUsername(String username);
    String crearUsuario(UsuarioDTO usuarioDTO);
    void eliminarUsuario(String usuarioId);
    void modificarUsuario(String usuarioId, UsuarioDTO usuarioDTO);
}
