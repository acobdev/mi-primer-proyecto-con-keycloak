package dev.acobano.keycloak.providers.policies;

import dev.acobano.keycloak.providers.policies.MiDolarCredencialPolicyProvider;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PasswordPolicyProviderFactory;

public class MiDolarCredencialPolicyProviderFactory implements PasswordPolicyProviderFactory
{
    private static final String PROVIDER_ID = "mi-dolar-credencial-policy";
    private static final MiDolarCredencialPolicyProvider SINGLETON = new MiDolarCredencialPolicyProvider();

    @Override
    public String getDisplayName() {
        return "Mi politica de credencial dolar";
    }

    @Override
    public String getConfigType() {
        return null;
    }

    @Override
    public String getDefaultConfigValue() {
        return null;
    }

    @Override
    public boolean isMultiplSupported() {
        return false;
    }

    @Override
    public PasswordPolicyProvider create(KeycloakSession keycloakSession) {
        return SINGLETON;
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
