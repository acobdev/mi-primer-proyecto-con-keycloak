package dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces;

import dev.acobano.miprimerproyectoconkeycloak.dto.ListaRolesDTO;
import org.keycloak.representations.idm.RoleRepresentation;
import dev.acobano.miprimerproyectoconkeycloak.dto.RolDTO;
import java.util.List;

public interface IServicioRoles
{
    ListaRolesDTO listarRoles();
    List<RoleRepresentation> listarRolesReino();
    List<RoleRepresentation> listarRolesCliente(String clienteUuid);
    RoleRepresentation buscarRolPorNombre(String nombreRol);
    RoleRepresentation crearRolReino(RolDTO rolDTO);
    RoleRepresentation crearRolCliente(String clienteUuid, RolDTO rolDTO);
    void modificarRolReino(String nombreRol, RolDTO rolDTO);
    void modificarRolCliente(String clienteUuid, String nombreRol, RolDTO rolDTO);
    void eliminarRolReino(String nombreRol);
    void eliminarRolCliente(String clienteUuid, String nombreRol);
    void asignarRolReinoAUsuario(String usuarioUuid, String nombreRol);
    void asignarRolClienteAUsuario(String clienteUuid, String usuarioUuid, String nombreRol);
    void desasignarRolReinoAUsuario(String usuarioUuid, String nombreRol);
    void desasignarRolClienteAUsuario(String clienteUuid, String usuarioUuid, String nombreRol);
    void asignarRolReinoAGrupo(String grupoUuid, String nombreRol);
    void asignarRolClienteAGrupo(String clienteUuid, String grupoUuid, String nombreRol);
    void desasignarRolReinoAGrupo(String grupoUuid, String nombreRol);
    void desasignarRolClienteAGrupo(String clienteUuid, String grupoUuid, String nombreRol);
}
