package dev.acobano.miprimerproyectoconkeycloak.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
public class ListaRolesDTO
{
    List<RoleRepresentation> rolesReino;
    Map<String, List<RoleRepresentation>> rolesCliente;
}
