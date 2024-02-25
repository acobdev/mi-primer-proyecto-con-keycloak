package dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces;

import dev.acobano.miprimerproyectoconkeycloak.dto.ClienteDTO;
import org.keycloak.representations.idm.ClientRepresentation;

import java.util.List;

public interface IServicioClientes
{
    List<ClientRepresentation> listarClientes();
    List<ClientRepresentation> buscarClientePorUuid(String clienteUuid);
    List<ClientRepresentation> buscarClientePorId(String clienteId);
    ClientRepresentation crearCliente(ClienteDTO clienteDTO);
    void modificarCliente(String clienteUuid, ClienteDTO clienteDTO);
    void eliminarCliente(String clienteUuid);
}
