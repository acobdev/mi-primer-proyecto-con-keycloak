package dev.acobano.miprimerproyectoconkeycloak.servicios.implementaciones;

import dev.acobano.miprimerproyectoconkeycloak.dto.UsuarioDTO;
import dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces.IServicioUsuarios;
import dev.acobano.miprimerproyectoconkeycloak.utiles.KeycloakProveedor;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class KeycloakServicioUsuariosImpl implements IServicioUsuarios
{
    /**
     * Método que devuelve la lista de usuarios registrados en nuestro reino Keycloak.
     * @return Colección de objetos de la clase 'UserRepresentation'.
     */
    @Override
    public List<UserRepresentation> listarUsuarios()
    {
        return KeycloakProveedor.getRecursoReino()
                .users()
                .list();
    }

    /**
     * Método para buscar en nuestro reino Keycloak aquel usuario cuyo parámetro
     * 'username' coincida con el introducido por parámetro de entrada.
     * @param username Cadena de texto con el nombre de usuario a buscar en el sistema.
     * @return Colección de objetos de la clase 'UserRepresentation'.
     */
    @Override
    public List<UserRepresentation> buscarUsuarioPorUsername(String username)
    {
        return KeycloakProveedor.getRecursoReino()
                .users()
                .searchByUsername(username, true);
    }

    /**
     * Método para crear un nuevo usuario en nuestro reino Keycloak.
     * @param usuarioDTO DTO de entrada con los datos del usuario a introducir en el sistema.
     * @return Cadena de texto con el mensaje de respuesta a la creación del usuario en Keycloak.
     */
    @Override
    public String crearUsuario(UsuarioDTO usuarioDTO)
    {
        UsersResource recursoUsuarios = KeycloakProveedor.getRecursosUsuarios();
        UserRepresentation representacionUsuario = new UserRepresentation();
        representacionUsuario.setUsername(usuarioDTO.getUsername());
        representacionUsuario.setFirstName(usuarioDTO.getFirstName());
        representacionUsuario.setLastName(usuarioDTO.getLastName());
        representacionUsuario.setEmail(usuarioDTO.getEmail());
        representacionUsuario.setEmailVerified(true);
        representacionUsuario.setEnabled(true);
        Response respuesta = recursoUsuarios.create(representacionUsuario);
        int estado = respuesta.getStatus();

        if (estado == 201)
        {
            //Inserción de las credenciales al usuario:
            String ruta = respuesta.getLocation().getPath();
            String usuarioId = ruta.substring(ruta.lastIndexOf("/") + 1);

            CredentialRepresentation credencial = new CredentialRepresentation();
            credencial.setTemporary(false);
            credencial.setType(OAuth2Constants.PASSWORD);
            credencial.setValue(usuarioDTO.getPassword());

            recursoUsuarios.get(usuarioId).resetPassword(credencial);

            //Asignación de los roles al usuario:
            RealmResource recursoReino = KeycloakProveedor.getRecursoReino();
            List<RoleRepresentation> listaRoles;

            if (Objects.isNull(usuarioDTO.getRoles()) || usuarioDTO.getRoles().isEmpty()) {
                //Si no se especifican explícitamente los roles, se pondrá el rol de ENCARGADOS por defecto:
                listaRoles = List.of(recursoReino.roles().get("ENCARGADO-rol-reino").toRepresentation());
            } else {
                //En caso contrario, les pasamos aquellos que vienen en el DTO:
                listaRoles = recursoReino.roles()
                        .list()
                        .stream()
                        .filter(rol -> usuarioDTO.getRoles()
                                .stream()
                                .anyMatch(nombreRol -> nombreRol.equalsIgnoreCase(rol.getName())))
                        .toList();
            }

            recursoReino.users()
                    .get(usuarioId)
                    .roles()
                    .realmLevel()
                    .add(listaRoles);

            return "Usuario creado satisfactoriamente en nuestro reino Keycloak";
        }
        //Controlamos que el usuario no esté ya creado:
        else if (estado == 409) {
            log.error("El usuario introducido ya se encuentra en el sistema.");
            return "El usuario introducido ya se encuentra en el sistema.";
        }
        //En caso de existir cualquier otro error, lo propagamos hacia las capas exteriores:
        else {
            log.error("Error en el proceso de creación de usuarios, por favor, póngase en contacto con el administrador.");
            return "Error en el proceso de creación de usuarios, por favor, póngase en contacto con el administrador.";
        }
    }

    /**
     * Método para eliminar a un usuario de un reino Keycloak.
     * @param usuarioId Cadena de texto con el identificador del usuario a eliminar del sistema.
     */
    @Override
    public void eliminarUsuario(String usuarioId)
    {
        KeycloakProveedor.getRecursosUsuarios()
                .get(usuarioId)
                .remove();
    }

    /**
     * Método para actualizar los datos de un usuario ya existente en nuestro reino Keycloak.
     * @param usuarioId Cadena de texto con el identificador del usuario a modificar en el sistema.
     * @param usuarioDTO
     */
    @Override
    public void modificarUsuario(String usuarioId, UsuarioDTO usuarioDTO)
    {
        //En primer lugar, creamos un objeto de credenciales específico de Keycloak:
        CredentialRepresentation credenciales = new CredentialRepresentation();
        credenciales.setTemporary(false);
        credenciales.setType(OAuth2Constants.PASSWORD);
        credenciales.setValue(usuarioDTO.getPassword());

        //Instanciamos un objeto UserRepresentation para intreducir los valores del DTO:
        UserRepresentation representacionUsuario = new UserRepresentation();
        representacionUsuario.setUsername(usuarioDTO.getUsername());
        representacionUsuario.setFirstName(usuarioDTO.getFirstName());
        representacionUsuario.setLastName(usuarioDTO.getLastName());
        representacionUsuario.setEmail(usuarioDTO.getEmail());
        representacionUsuario.setEmailVerified(true);
        representacionUsuario.setEnabled(true);
        representacionUsuario.setCredentials(Collections.singletonList(credenciales));

        //Guardamos los cambios en el reino Keycloak:
        UserResource recursoUsuario = KeycloakProveedor.getRecursosUsuarios().get(usuarioId);
        recursoUsuario.update(representacionUsuario);
    }
}
