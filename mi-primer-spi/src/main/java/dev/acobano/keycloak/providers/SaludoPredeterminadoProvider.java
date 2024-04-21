package dev.acobano.keycloak.providers;

import dev.acobano.keycloak.providers.spi.SaludoProvider;
import org.keycloak.models.KeycloakContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SaludoPredeterminadoProvider implements SaludoProvider {

    private static Logger logger = Logger.getLogger("dev.acobano.keycloak.providers.SaludoPredeterminadoProvider");

    private final KeycloakContext context;


    public SaludoPredeterminadoProvider(KeycloakContext context) {
        this.context = context;
    }

    @Override
    public void saludar() {
        if (context.getAuthenticationSession() != null) {
            logger.log(Level.INFO, "¡Bienvenido a Keycloak, {0}!", context.getAuthenticationSession().getAuthenticatedUser().getFirstName());
        } else {
            logger.log(Level.INFO, "¡Bienvenido a Keycloak, extraño!");
        }
    }

    @Override
    public void close() {

    }
}
