package dev.acobano.miprimerproyectoconkeycloak.controladores;

import dev.acobano.miprimerproyectoconkeycloak.dto.ListaRolesDTO;
import dev.acobano.miprimerproyectoconkeycloak.dto.RolDTO;
import dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces.IServicioRoles;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//<>
@RestController
@RequestMapping("/api/keycloak/roles")
@PreAuthorize("hasRole('ADMINISTRADOR-rol-cliente')")
public class ControladorRoles
{
    @Autowired
    private IServicioRoles servicioRoles;


                /** *** ENDPOINTS CRUD DE ROLES **/

    @GetMapping
    public ResponseEntity<ListaRolesDTO> listarRoles()
    {
        return ResponseEntity.ok(this.servicioRoles.listarRoles());
    }

    @GetMapping("/reino")
    public ResponseEntity<List<RoleRepresentation>> listarRolesReino()
    {
        return ResponseEntity.ok(this.servicioRoles.listarRolesReino());
    }

    @GetMapping("/cliente/{clienteUuid}")
    public ResponseEntity<List<RoleRepresentation>> listarRolesCliente(@PathVariable String clienteUuid)
    {
        return ResponseEntity.ok(this.servicioRoles.listarRolesCliente(clienteUuid));
    }

    @PostMapping("/reino")
    public ResponseEntity<RoleRepresentation> crearRolReino(@RequestBody RolDTO rolDTO)
    {
        return ResponseEntity.ok(this.servicioRoles.crearRolReino(rolDTO));
    }

    @PostMapping("/cliente/{clienteUuid}")
    public ResponseEntity<RoleRepresentation> crearRolCliente(@PathVariable String clienteUuid, @RequestBody RolDTO rolDTO)
    {
        return ResponseEntity.ok(this.servicioRoles.crearRolCliente(clienteUuid, rolDTO));
    }

    @PutMapping("/reino/{nombreRol}")
    public ResponseEntity<String> modificarDatosRolReino(@PathVariable String nombreRol, @RequestBody RolDTO rolDTO)
    {
        this.servicioRoles.modificarRolReino(nombreRol, rolDTO);
        return ResponseEntity.ok("El rol a nivel de reino con nombre " + nombreRol + " ha sido modificado satisfactoriamente en el sistema.");
    }

    @PutMapping("/{rolUuid}/cliente/{clienteUuid}")
    public ResponseEntity<String> modificarDatosRolCliente(@PathVariable String nombreRol, @PathVariable String clienteUuid, @RequestBody RolDTO rolDTO)
    {
        this.servicioRoles.modificarRolCliente(clienteUuid, nombreRol, rolDTO);
        return ResponseEntity.ok("El rol " + nombreRol + " a nivel de cliente con UUID " + clienteUuid + " ha sido modificado satisfactoriamente en el sistema.");
    }

    @DeleteMapping("/reino/{nombreRol}")
    public ResponseEntity<String> eliminarRolReino(@PathVariable String nombreRol)
    {
        this.servicioRoles.eliminarRolReino(nombreRol);
        return ResponseEntity.ok("El rol con nombre " + nombreRol + " a nivel de reino ha sido eliminado satisfactoriamente en el sistema.");
    }

    @DeleteMapping("{nombreRol}/cliente/{clienteUuid}")
    public ResponseEntity<String> eliminarRolCliente(@PathVariable String nombreRol, @PathVariable String clienteUuid)
    {
        this.servicioRoles.eliminarRolCliente(clienteUuid, nombreRol);
        return ResponseEntity.ok("El rol " + nombreRol + "a nivel de cliente con UUID " + clienteUuid + " ha sido eliminado satisfactoriamente del sistema.");
    }

                /*** ENDPOINTS DE ASIGNACIÓN Y DESASIGNACIÓN DE ROLES ***/
    @PutMapping("/reino/{nombreRol}/usuario/{uuidUsuario}")
    public ResponseEntity<String> asignarRolReinoAUsuario(@PathVariable String nombreRol, @PathVariable String uuidUsuario)
    {
        this.servicioRoles.asignarRolReinoAUsuario(uuidUsuario, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol a nivel de reino con nombre " + nombreRol + " ha sido asignado satisfactoriamente al usuario " + uuidUsuario);
    }

    @DeleteMapping("/reino/{nombreRol}/usuario/{uuidUsuario}")
    public ResponseEntity<String> desasignarRolReinoAUsuario(@PathVariable String nombreRol, @PathVariable String uuidUsuario)
    {
        this.servicioRoles.desasignarRolReinoAUsuario(uuidUsuario, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol a nivel de reino con nombre " + nombreRol + " ha sido desasignado satisfactoriamente al usuario " + uuidUsuario);
    }

    @PutMapping("/{nombreRol}/cliente/{clienteUuid}/usuario/{uuidUsuario}")
    public ResponseEntity<String> asignarRolClienteAUsuario(@PathVariable String nombreRol, @PathVariable String clienteUuid, @PathVariable String uuidUsuario)
    {
        this.servicioRoles.asignarRolClienteAUsuario(clienteUuid, uuidUsuario, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol con nombre " + nombreRol + " a nivel de cliente con UUID " + clienteUuid + "ha sido asignado satisfactoriamente al usuario " + uuidUsuario);
    }

    @DeleteMapping("/{nombreRol}/cliente/{clienteUuid}/usuario/{uuidUsuario}")
    public ResponseEntity<String> desasignarRolClienteAUsuario(@PathVariable String nombreRol, @PathVariable String clienteUuid, @PathVariable String uuidUsuario)
    {
        this.servicioRoles.desasignarRolClienteAUsuario(clienteUuid, uuidUsuario, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol con nombre " + nombreRol + " a nivel de cliente con UUID " + clienteUuid + "ha sido desasignado satisfactoriamente al usuario " + uuidUsuario);
    }

    @PutMapping("/reino/{nombreRol}/grupo/{uuidGrupo}")
    public ResponseEntity<String> asignarRolReinoAGrupo(@PathVariable String nombreRol, @PathVariable String uuidGrupo)
    {
        this.servicioRoles.asignarRolReinoAGrupo(uuidGrupo, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol a nivel de reino con nombre " + nombreRol +
                        " ha sido asignado satisfactoriamente al grupo " + uuidGrupo);
    }

    @DeleteMapping("/reino/{nombreRol}/grupo/{uuidGrupo}")
    public ResponseEntity<String> desasignarRolReinoAGrupo(@PathVariable String nombreRol, @PathVariable String uuidGrupo)
    {
        this.servicioRoles.desasignarRolReinoAGrupo(uuidGrupo, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol a nivel de reino con nombre " + nombreRol +
                        " ha sido desasignado satisfactoriamente al grupo " + uuidGrupo);
    }

    @PutMapping("/{nombreRol}/cliente/{clienteUuid}/grupo/{uuidGrupo}")
    public ResponseEntity<String> asignarRolClienteAGrupo(@PathVariable String nombreRol, @PathVariable String clienteUuid, @PathVariable String uuidGrupo)
    {
        this.servicioRoles.asignarRolClienteAGrupo(clienteUuid, uuidGrupo, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol con nombre " + nombreRol + " a nivel de cliente con UUID " + clienteUuid +
                        "ha sido asignado satisfactoriamente al grupo " + uuidGrupo);
    }

    @DeleteMapping("/{nombreRol}/cliente/{clienteUuid}/grupo/{uuidGrupo}")
    public ResponseEntity<String> desasignarRolClienteAGrupo(@PathVariable String nombreRol, @PathVariable String clienteUuid, @PathVariable String uuidGrupo)
    {
        this.servicioRoles.desasignarRolClienteAGrupo(clienteUuid, uuidGrupo, nombreRol);
        return ResponseEntity.accepted()
                .body("El rol con nombre " + nombreRol + " a nivel de cliente con UUID " + clienteUuid +
                        "ha sido desasignado satisfactoriamente al grupo " + uuidGrupo);
    }
}
