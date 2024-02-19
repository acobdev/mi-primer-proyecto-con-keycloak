package dev.acobano.miprimerproyectoconkeycloak.controladores;

import dev.acobano.miprimerproyectoconkeycloak.dto.UsuarioDTO;
import dev.acobano.miprimerproyectoconkeycloak.servicios.interfaces.IServicioUsuarios;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

//<>
@RestController
@RequestMapping("/api/keycloak/usuarios")
@PreAuthorize("hasRole('ADMINISTRADOR-rol-cliente')")
public class ControladorUsuarios
{
    @Autowired
    private IServicioUsuarios servicioUsuarios;

    @GetMapping
    public ResponseEntity<List<UserRepresentation>> listarUsuarios()
    {
        return ResponseEntity.ok(this.servicioUsuarios.listarUsuarios());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<UserRepresentation>> buscarUsuariosPorUsername(@PathVariable String username)
    {
        return ResponseEntity.ok(this.servicioUsuarios.buscarUsuarioPorUsername(username));
    }

    @PostMapping()
    public ResponseEntity<String> crearNuevoUsuario(@RequestBody UsuarioDTO usuarioDTO)
            throws URISyntaxException
    {
        String respuesta = this.servicioUsuarios.crearUsuario(usuarioDTO);
        return ResponseEntity.created(new URI("/api/keycloak/usuarios")).body(respuesta);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<String> modificarDatosUsuario(@PathVariable String usuarioId, @RequestBody UsuarioDTO usuarioDTO)
    {
        this.servicioUsuarios.modificarUsuario(usuarioId, usuarioDTO);
        return ResponseEntity.ok("Usuario actualizado correctamente en el sistema.");
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String usuarioId)
    {
        this.servicioUsuarios.eliminarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
