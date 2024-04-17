package dev.acobano.keycloak.providers.requiredactions;

import org.keycloak.Config;
import org.keycloak.authentication.RequiredActionFactory;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class MiEncuestaOpinionRequiredActionProviderFactory implements RequiredActionFactory {

    private static final String PROVIDER_ID = "mi-encuesta-opinion";

    @Override
    public String getDisplayText() {
        return "¿Cómo te sientes hoy?";
    }

    @Override
    public RequiredActionProvider create(KeycloakSession session) {
        return null;
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
