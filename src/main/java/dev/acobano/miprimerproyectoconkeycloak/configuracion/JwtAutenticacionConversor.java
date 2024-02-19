package dev.acobano.miprimerproyectoconkeycloak.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class JwtAutenticacionConversor implements Converter<Jwt, AbstractAuthenticationToken>
{
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principle-attribute}")
    private String atributoNombreUsuario;

    @Value("${jwt.auth.converter.resource-id}")
    private String clienteId;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt)
    {
        Collection<GrantedAuthority> permisos = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extraerRoles(jwt).stream()
                ).toList();
        return new JwtAuthenticationToken(jwt, permisos, getNombreSolicitud(jwt));
    }

    private Collection<? extends GrantedAuthority> extraerRoles(Jwt jwt)
    {
        Map<String, Object> recursosAcceso;
        Map<String, Object> recurso;
        Collection<String> roles;

        if(Objects.isNull(jwt.getClaim("resource_access")))
            return Set.of();
        else
        {
            recursosAcceso = jwt.getClaim("resource_access");

            if (Objects.isNull(recursosAcceso.get(clienteId)))
                return Set.of();
            else
            {
                recurso = (Map<String, Object>) recursosAcceso.get(clienteId);

                if (Objects.isNull(recurso.get("roles")))
                    return Set.of();
                else
                {
                    roles = (Collection<String>) recurso.get("roles");
                    return  roles.stream()
                            .map(rol -> new SimpleGrantedAuthority("ROLE_".concat(rol)))
                            .toList();
                }
            }
        }
    }

    private String getNombreSolicitud(Jwt jwt)
    {
        String nombreSolicitud = JwtClaimNames.SUB;

        if (!Objects.isNull(atributoNombreUsuario))
            nombreSolicitud = atributoNombreUsuario;

        return jwt.getClaim(nombreSolicitud);
    }
}
