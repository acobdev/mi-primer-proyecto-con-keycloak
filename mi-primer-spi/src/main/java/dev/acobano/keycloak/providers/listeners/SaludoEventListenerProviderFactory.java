package dev.acobano.keycloak.providers.listeners;

import dev.acobano.keycloak.providers.spi.SaludoProvider;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SaludoEventListenerProviderFactory implements EventListenerProviderFactory {

    private static final String PROVIDER_ID = "saludo-event-listener-provider";

    @Override
    public EventListenerProvider create(KeycloakSession session) {
        return new SaludoEventListenerProvider(session.getProvider(SaludoProvider.class));
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
