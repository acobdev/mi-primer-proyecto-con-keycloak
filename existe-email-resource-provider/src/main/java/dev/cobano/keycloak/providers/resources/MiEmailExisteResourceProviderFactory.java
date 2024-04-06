package dev.cobano.keycloak.providers.resources;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class MiEmailExisteResourceProviderFactory implements RealmResourceProviderFactory
{
    private static final String PROVIDER_ID = "mi-email-existe-provider-factory";


    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new MiEmailExisteResourceProvider(session);
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
