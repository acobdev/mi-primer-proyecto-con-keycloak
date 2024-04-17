package dev.acobano.keycloak.providers.requiredactions;

import org.keycloak.authentication.RequiredActionContext;
import org.keycloak.authentication.RequiredActionProvider;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.jpa.entities.UserAttributeEntity;
import org.keycloak.models.jpa.entities.UserEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

public class MiEncuestaOpinionRequiredActionProvider implements RequiredActionProvider {
    @Override
    public void evaluateTriggers(RequiredActionContext context) {
        // Este método será el encargado de comprobar si se cumple la
        // condición deseada para que la acción requerida sea disparada.

        //En este caso específico, haremos que la encuesta salte los miércoles:
        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.WEDNESDAY)) {
            context.getUser().addRequiredAction("mi-encuesta-opinion");
        }
    }

    @Override
    public void requiredActionChallenge(RequiredActionContext context) {
        // Este método muestra en pantalla la acción requerida, siempre
        // y cuando haya pasado con éxito el primer método.

        // En este caso específico, crearemos un formulario de opinión:
        context.challenge(context.form().createForm("mi-encuesta-opinion-form.ftl"));
    }

    @Override
    public void processAction(RequiredActionContext context) {
        // En primer lugar, deberemos saber si el usuario ha rellenado o no la encuesta:
        var saltar = context.getHttpRequest().getDecodedFormParameters().getFirst("saltar");

        // En caso de que el usuario haya presionado el botón de saltar, escapamos del action:
        if (saltar != null) {
            context.success();
            return;
        }

        //En caso contrario, guardamos el valor escogido en una variable:
        var opinion = context.getHttpRequest().getDecodedFormParameters().getFirst("opinion");

        // Usamos el JpaConnectionProvider para obtener el entityManager con el cual
        // Keycloak usa Hibernate como ORM para hacer procesos CRUD con la BD.
        var entityManager = context.getSession().getProvider(JpaConnectionProvider.class).getEntityManager();

        // Guardaremos el atributo "opinión" dentro de su propio objeto para JPA:
        var userAttribute = new UserAttributeEntity();
        userAttribute.setName("Opinion");
        userAttribute.setValue(opinion);

        // Llamamos a la entidad del usuario que ha realizado la encuesta y le guardamos el nuevo atributo:
        var userEntity = new UserEntity();
        userEntity.setId(context.getUser().getId());
        userAttribute.setUser(userEntity);
        userAttribute.setId(UUID.randomUUID().toString());
        entityManager.persist(userAttribute);

        // Por último, indicamos al sistema que se ha terminado la lógica de la Required Action:
        context.success();
    }

    @Override
    public void close() {

    }
}
