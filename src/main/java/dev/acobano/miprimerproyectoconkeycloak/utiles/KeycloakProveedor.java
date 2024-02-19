package dev.acobano.miprimerproyectoconkeycloak.utiles;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;

public class KeycloakProveedor
{
    private static final String URL_KEYCLOAK = "http://localhost:8081";
    private static final String NOMBRE_REINO = "mi-reino-de-pruebas";
    private static final String REINO_MAESTRO = "master";
    private static final String CLIENTE_ADMIN = "admin-cli";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_CREDENCIAL = "KeycloakAdmin1234";
    private static final String CLIENT_SECRET = "rxjoT6QuoliAm1yh9fvh7f8KDdBfurpp";

    public static RealmResource getRecursoReino()
    {
        Keycloak keycloak = KeycloakBuilder.builder()
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

        return keycloak.realm(NOMBRE_REINO);
    }

    public static UsersResource getRecursoUsuarios()
    {
        RealmResource reino = getRecursoReino();
        return reino.users();
    }
}
