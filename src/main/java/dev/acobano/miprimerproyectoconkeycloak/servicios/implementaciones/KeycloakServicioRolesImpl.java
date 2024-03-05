package dev.acobano.miprimerproyectoconkeycloak.servicios.implementaciones;

import dev.acobano.miprimerproyectoconkeycloak.dto.ListaRolesDTO;
import dev.acobano.miprimerproyectoconkeycloak.dto.RolDTO;
import dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces.IServicioRoles;
import dev.acobano.miprimerproyectoconkeycloak.utiles.KeycloakProveedor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class KeycloakServicioRolesImpl implements IServicioRoles
{
    @Override
    public ListaRolesDTO listarRoles()
    {
        ListaRolesDTO respuesta = new ListaRolesDTO();
        respuesta.setRolesReino(this.listarRolesReino());
        respuesta.setRolesCliente(new HashMap<>());
        List<ClientRepresentation> clientes = KeycloakProveedor.getRecursoReino().clients().findAll();

        for (ClientRepresentation cliente : clientes)
        {
            ClientResource recursoCliente = KeycloakProveedor.getRecursoReino().clients().get(cliente.getId());
            RolesResource recursoRolCliente = recursoCliente.roles();
            List<RoleRepresentation> rolesCliente = recursoRolCliente.list();
            respuesta.getRolesCliente().put(cliente.getClientId(), rolesCliente);
        }

        return respuesta;
    }

    @Override
    public List<RoleRepresentation> listarRolesReino()
    {
        return KeycloakProveedor.getRecursoReino()
                .roles()
                .list();
    }

    @Override
    public List<RoleRepresentation> listarRolesCliente(String clienteUuid)
    {
        return KeycloakProveedor.getRecursoReino()
                .clients()
                .get(clienteUuid)
                .roles()
                .list();
    }

    @Override
    public RoleRepresentation buscarRolPorNombre(String nombreRol)
    {
        //Buscamos en todos los roles del reino:
        List<RoleRepresentation> listaRolesReino = this.listarRolesReino();

        for(RoleRepresentation rolReino : listaRolesReino)
            if (rolReino.getName().equalsIgnoreCase(nombreRol))
                return rolReino;

        //Listamos todos los clientes que tiene el reino:
        List<ClientRepresentation> clientes = KeycloakProveedor.getRecursosClientes().findAll();

        //Buscamos en todos los roles de cada uno de los clientes:
        for(ClientRepresentation cliente : clientes)
        {
            RolesResource recursoRolesCliente = KeycloakProveedor.getRecursosRolesCliente(cliente.getClientId());
            //List<RoleRepresentation> listaRolesCliente = cliente.
        }

        return null;
    }

    @Override
    public RoleRepresentation crearRolReino(RolDTO rolDTO)
    {
        RolesResource recursoRoles = KeycloakProveedor.getRecursosRolesReino();
        RoleRepresentation rolACrear = new RoleRepresentation();
        rolACrear.setName(rolDTO.getNombre());
        rolACrear.setDescription(rolDTO.getDescripcion());
        rolACrear.setClientRole(false);
        recursoRoles.create(rolACrear);
        return rolACrear;
    }

    @Override
    public RoleRepresentation crearRolCliente(String clienteUuid, RolDTO rolDTO)
    {
        RolesResource recursosRoles = KeycloakProveedor.getRecursoReino().clients().get(clienteUuid).roles();
        RoleRepresentation rolACrear = new RoleRepresentation();
        rolACrear.setName(rolDTO.getNombre());
        rolACrear.setDescription(rolDTO.getDescripcion());
        rolACrear.setClientRole(true);
        recursosRoles.create(rolACrear);
        //TODO: Ver cómo meter el RoleRepresentation del objeto ya creado
        return rolACrear;
    }

    @Override
    public void modificarRolReino(String nombreRol, RolDTO rolDTO)
    {
        RoleRepresentation representacionRol = new RoleRepresentation();
        representacionRol.setName(rolDTO.getNombre());
        representacionRol.setDescription(rolDTO.getDescripcion());
        RoleResource recursoRol = KeycloakProveedor.getRecursosRolesReino().get(nombreRol);
        recursoRol.update(representacionRol);
    }

    @Override
    public void modificarRolCliente(String clienteUuid, String nombreRol, RolDTO rolDTO)
    {
        RoleRepresentation representacionRol = new RoleRepresentation();
        representacionRol.setName(rolDTO.getNombre());
        representacionRol.setDescription(rolDTO.getDescripcion());
        RoleResource recursoRol = KeycloakProveedor.getRecursosRolesCliente(clienteUuid).get(nombreRol);
        recursoRol.update(representacionRol);
    }

    @Override
    public void eliminarRolReino(String nombreRol)
    {
        KeycloakProveedor.getRecursosRolesReino()
                .get(nombreRol)
                .remove();
    }

    @Override
    public void eliminarRolCliente(String clienteUuid, String nombreRol)
    {
        KeycloakProveedor.getRecursosRolesCliente(clienteUuid)
                .get(nombreRol)
                .remove();
    }

    @Override
    public void asignarRolReinoAUsuario(String usuarioUuid, String nombreRol)
    {
        UserResource recursoUsuario = KeycloakProveedor.getRecursosUsuarios().get(usuarioUuid);
        RolesResource recursoRolesReino = KeycloakProveedor.getRecursosRolesReino();
        RoleRepresentation representacionRol = recursoRolesReino.get(nombreRol).toRepresentation();
        recursoUsuario.roles().realmLevel().add(Collections.singletonList(representacionRol));
    }

    @Override
    public void asignarRolClienteAUsuario(String clienteUuid, String usuarioUuid, String nombreRol)
    {
        UserResource recursoUsuario = KeycloakProveedor.getRecursosUsuarios().get(usuarioUuid);
        RolesResource recursoRolesCliente = KeycloakProveedor.getRecursosRolesCliente(clienteUuid);
        RoleRepresentation representacionRol = recursoRolesCliente.get(nombreRol).toRepresentation();
        recursoUsuario.roles().clientLevel(clienteUuid).add(Collections.singletonList(representacionRol));
    }

    @Override
    public void desasignarRolReinoAUsuario(String usuarioUuid, String nombreRol)
    {
        UserResource recursoUsuario = KeycloakProveedor.getRecursosUsuarios().get(usuarioUuid);
        RolesResource recursoRolesReino = KeycloakProveedor.getRecursosRolesReino();
        RoleRepresentation representacionRol = recursoRolesReino.get(nombreRol).toRepresentation();
        recursoUsuario.roles().realmLevel().remove(Collections.singletonList(representacionRol));
    }

    @Override
    public void desasignarRolClienteAUsuario(String clienteUuid, String usuarioUuid, String nombreRol)
    {
        UserResource recursoUsuario = KeycloakProveedor.getRecursosUsuarios().get(usuarioUuid);
        RolesResource recursoRolesCliente = KeycloakProveedor.getRecursosRolesCliente(clienteUuid);
        RoleRepresentation representacionRol = recursoRolesCliente.get(nombreRol).toRepresentation();
        recursoUsuario.roles().clientLevel(clienteUuid).remove(Collections.singletonList(representacionRol));
    }

    @Override
    public void asignarRolReinoAGrupo(String grupoUuid, String nombreRol)
    {
        GroupResource recursoGrupo = KeycloakProveedor.getRecursoGrupos().group(grupoUuid);
        RolesResource recursoRolesReino = KeycloakProveedor.getRecursosRolesReino();
        RoleRepresentation representacionRol = recursoRolesReino.get(nombreRol).toRepresentation();
        recursoGrupo.roles().realmLevel().add(Collections.singletonList(representacionRol));
    }

    @Override
    public void asignarRolClienteAGrupo(String clienteUuid, String grupoUuid, String nombreRol)
    {
        GroupResource recursoGrupo = KeycloakProveedor.getRecursoGrupos().group(grupoUuid);
        RolesResource recursoRolesCliente = KeycloakProveedor.getRecursosRolesCliente(clienteUuid);
        RoleRepresentation representacionRol = recursoRolesCliente.get(nombreRol).toRepresentation();
        recursoGrupo.roles().clientLevel(clienteUuid).add(Collections.singletonList(representacionRol));
    }

    @Override
    public void desasignarRolReinoAGrupo(String grupoUuid, String nombreRol)
    {
        GroupResource recursoGrupo = KeycloakProveedor.getRecursoGrupos().group(grupoUuid);
        RolesResource recursoRolesReino = KeycloakProveedor.getRecursosRolesReino();
        RoleRepresentation representacionRol = recursoRolesReino.get(nombreRol).toRepresentation();
        recursoGrupo.roles().realmLevel().remove(Collections.singletonList(representacionRol));
    }

    @Override
    public void desasignarRolClienteAGrupo(String clienteUuid, String grupoUuid, String nombreRol)
    {
        GroupResource recursoGrupo = KeycloakProveedor.getRecursoGrupos().group(grupoUuid);
        RolesResource recursoRolesCliente = KeycloakProveedor.getRecursosRolesCliente(clienteUuid);
        RoleRepresentation representacionRol = recursoRolesCliente.get(nombreRol).toRepresentation();
        recursoGrupo.roles().clientLevel(clienteUuid).remove(Collections.singletonList(representacionRol));
    }
}
