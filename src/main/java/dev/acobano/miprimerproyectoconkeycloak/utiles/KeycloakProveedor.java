package dev.acobano.miprimerproyectoconkeycloak.utiles;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.springframework.beans.factory.annotation.Value;

public class KeycloakProveedor
{
    @Value("http://localhost:8081")
    private static final String URL_KEYCLOAK = "http://localhost:8081";
    @Value("mi-reino-de-pruebas")
    private static final String NOMBRE_REINO = "mi-reino-de-pruebas";
    @Value("master")
    private static final String REINO_MAESTRO = "master";
    @Value("admin-cli")
    private static final String CLIENTE_ADMIN = "admin-cli";
    @Value("admin")
    private static final String ADMIN_USERNAME = "admin";
    @Value("KeycloakAdmin1234")
    private static final String ADMIN_CREDENCIAL = "KeycloakAdmin1234";
    @Value("rxjoT6QuoliAm1yh9fvh7f8KDdBfurpp")
    private static final String CLIENT_SECRET = "rxjoT6QuoliAm1yh9fvh7f8KDdBfurpp";

    private static Keycloak getKeycloak()
    {
        return KeycloakBuilder.builder()
                .serverUrl(URL_KEYCLOAK)
                .realm(REINO_MAESTRO)
                .username(ADMIN_USERNAME)
                .password(ADMIN_CREDENCIAL)
                .clientId(CLIENTE_ADMIN)
                .clientSecret(CLIENT_SECRET)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build())
                .build();
    }

    public static RealmResource getRecursoReino()
    {
        return getKeycloak().realm(NOMBRE_REINO);
    }

    public static UsersResource getRecursosUsuarios()
    {
        RealmResource reino = getRecursoReino();
        return reino.users();
    }

    public static ClientsResource getRecursosClientes()
    {
        return getRecursoReino().clients();
    }

    public static RolesResource getRecursosRolesReino()
    {
        return getRecursoReino().roles();
    }

    public static RolesResource getRecursosRolesCliente(String clienteUuid)
    {
        return getRecursosClientes().get(clienteUuid).roles();
    }

    public static GroupsResource getRecursoGrupos()
    {
        return  getRecursoReino().groups();
    }
}
