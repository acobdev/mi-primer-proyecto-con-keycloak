package dev.acobano.miprimerproyectoconkeycloak.controladores;

import dev.acobano.miprimerproyectoconkeycloak.dto.ClienteDTO;
import dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces.IServicioClientes;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/keycloak/clientes")
@PreAuthorize("hasRole('ADMINISTRADOR-rol-cliente')")
public class ControladorClientes
{
    @Autowired
    private IServicioClientes servicioClientes;

    @GetMapping
    public ResponseEntity<List<ClientRepresentation>> listarClientes()
    {
        return ResponseEntity.ok(this.servicioClientes.listarClientes());
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<ClientRepresentation>> buscarClientePorId(@PathVariable String clienteId)
    {
        return ResponseEntity.ok(this.servicioClientes.buscarClientePorUuid(clienteId));
    }

    @PostMapping
    public ResponseEntity<ClientRepresentation> crearNuevoCliente(@RequestBody ClienteDTO clienteDTO)
            throws URISyntaxException {
        return ResponseEntity.created(new URI("/api/keycloak/clientes"))
                .body(this.servicioClientes.crearCliente(clienteDTO));
    }

    @PutMapping("/{clienteUuid}")
    public ResponseEntity<String> actualizarDatosCliente(@PathVariable String clienteUuid, @RequestBody ClienteDTO clienteDTO)
    {
        this.servicioClientes.modificarCliente(clienteUuid, clienteDTO);
        return ResponseEntity.ok("El cliente con UUID " + clienteUuid + " ha sido modificado satisfactoriamente en el sistema.");
    }

    @DeleteMapping("/{clienteUuid}")
    public ResponseEntity<String> eliminarCliente(@PathVariable String clienteUuid)
    {
        this.servicioClientes.eliminarCliente(clienteUuid);
        return ResponseEntity.ok("El cliente con UUID " + clienteUuid + " ha sido eliminado satisfactoriamente del sistema.");
    }
}
