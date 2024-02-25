package dev.acobano.miprimerproyectoconkeycloak.servicios.implementaciones;

import dev.acobano.miprimerproyectoconkeycloak.dto.ClienteDTO;
import dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces.IServicioClientes;
import dev.acobano.miprimerproyectoconkeycloak.utiles.KeycloakProveedor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KeycloakServicioClientesImpl implements IServicioClientes
{
    @Override
    public List<ClientRepresentation> listarClientes()
    {
        return KeycloakProveedor.getRecursosClientes().findAll();
    }

    @Override
    public List<ClientRepresentation> buscarClientePorUuid(String clienteUuid)
    {
        return KeycloakProveedor.getRecursosClientes()
                .findAll()
                .stream()
                .filter(cliente -> cliente.getId().equalsIgnoreCase(clienteUuid))
                .toList();
    }

    @Override
    public List<ClientRepresentation> buscarClientePorId(String clienteId)
    {
        return KeycloakProveedor.getRecursosClientes()
                .findAll()
                .stream()
                .filter(cliente -> cliente.getClientId().equalsIgnoreCase(clienteId))
                .toList();
    }

    @Override
    public ClientRepresentation crearCliente(ClienteDTO clienteDTO)
    {
        ClientsResource recursosClientes = KeycloakProveedor.getRecursosClientes();
        ClientRepresentation representacionCliente = new ClientRepresentation();
        representacionCliente.setClientId(clienteDTO.getIdCliente());
        representacionCliente.setName(clienteDTO.getNombre());
        representacionCliente.setDescription(clienteDTO.getDescripcion());
        representacionCliente.setRootUrl(clienteDTO.getUrlRaiz());
        representacionCliente.setAdminUrl(clienteDTO.getUrlAdmin());
        representacionCliente.setBaseUrl(clienteDTO.getUrlBase());
        representacionCliente.setEnabled(clienteDTO.isEstaActivo());
        representacionCliente.setSecret(clienteDTO.getClientSecret());
        recursosClientes.create(representacionCliente);
        return representacionCliente;
    }

    @Override
    public void modificarCliente(String clienteUuid, ClienteDTO clienteDTO)
    {
        ClientRepresentation representacionCliente =  new ClientRepresentation();
        representacionCliente.setClientId(clienteDTO.getIdCliente());
        representacionCliente.setName(clienteDTO.getNombre());
        representacionCliente.setDescription(clienteDTO.getDescripcion());
        representacionCliente.setRootUrl(clienteDTO.getUrlRaiz());
        representacionCliente.setAdminUrl(clienteDTO.getUrlAdmin());
        representacionCliente.setBaseUrl(clienteDTO.getUrlBase());
        representacionCliente.setEnabled(clienteDTO.isEstaActivo());
        representacionCliente.setSecret(clienteDTO.getClientSecret());
        ClientsResource recursoClientes = KeycloakProveedor.getRecursosClientes();
        recursoClientes.get(clienteUuid).update(representacionCliente);
    }

    @Override
    public void eliminarCliente(String clienteUuid)
    {
        KeycloakProveedor.getRecursosClientes()
                .get(clienteUuid)
                .remove();
    }
}
