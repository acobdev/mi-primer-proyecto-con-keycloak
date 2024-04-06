package dev.cobano.keycloak.providers.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

public class MiEmailExisteResourceProvider implements RealmResourceProvider
{
    private final KeycloakSession sesion;

    public MiEmailExisteResourceProvider(KeycloakSession sesion) {
        this.sesion = sesion;
    }

    @Override
    public Object getResource() {
        return new EmailExisteResource(sesion);
    }

    @Override
    public void close() {

    }

    public class EmailExisteResource
    {
        private final KeycloakSession sesion;

        public EmailExisteResource(KeycloakSession sesion) {
            this.sesion = sesion;
        }

        @GET
        public Object emailExiste(@QueryParam("email") String email)
        {
            return new EmailExisteResponse(
                    sesion.users().getUserByEmail(sesion.getContext().getRealm(), email)
                            != null);
        }
    }

    public class EmailExisteResponse
    {
        private boolean existe;

        public EmailExisteResponse(boolean existe) {
            this.existe = existe;
        }

        public boolean isExiste() {
            return existe;
        }

        public void setExiste(boolean existe) {
            this.existe = existe;
        }
    }
}
