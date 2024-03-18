package dev.acobano.keycloak.providers.policies;

import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.policy.PolicyError;

public class MiDolarCredencialPolicyProvider implements PasswordPolicyProvider
{
    @Override
    public PolicyError validate(RealmModel realmModel, UserModel userModel, String s) {
        return null;
    }

    @Override
    public PolicyError validate(String user, String password) {
        //Método que contiene la lógica de validación de la política de credenciales personalizada:
        return password.startsWith("$") && password.endsWith("$")
                ? null
                : new PolicyError("El campo 'contraseña' debe empezar y terminar con el carácter dólar '$'.");
    }

    @Override
    public Object parseConfig(String s) {
        return null;
    }

    @Override
    public void close() {

    }
}
