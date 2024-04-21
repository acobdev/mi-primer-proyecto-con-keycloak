package dev.acobano.keycloak.providers.listeners;

import dev.acobano.keycloak.providers.spi.SaludoProvider;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;

public class SaludoEventListenerProvider implements EventListenerProvider {

    private final SaludoProvider saludoProvider;

    public SaludoEventListenerProvider(SaludoProvider saludoProvider) {
        this.saludoProvider = saludoProvider;
    }

    @Override
    public void onEvent(Event event) {
        // Aquí es donde irá la lógica asociada al evento disparado escuchado por el Listener:
        if (event.getType().equals(EventType.LOGIN)) {
            saludoProvider.saludar();
        }
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {

    }

    @Override
    public void close() {

    }
}
