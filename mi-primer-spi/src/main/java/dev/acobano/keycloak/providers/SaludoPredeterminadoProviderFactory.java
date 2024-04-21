package dev.acobano.keycloak.providers;

import dev.acobano.keycloak.providers.spi.SaludoProvider;
import dev.acobano.keycloak.providers.spi.SaludoProviderFactory;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class SaludoPredeterminadoProviderFactory implements SaludoProviderFactory {

    private static final String PROVIDER_ID = "mi-saludo-predeterminado-provider";

    @Override
    public SaludoProvider create(KeycloakSession session) {
        return new SaludoPredeterminadoProvider(session.getContext());
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
