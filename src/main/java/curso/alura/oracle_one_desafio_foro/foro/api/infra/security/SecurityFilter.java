package curso.alura.oracle_one_desafio_foro.foro.api.infra.security;

import curso.alura.oracle_one_desafio_foro.foro.api.domain.Usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verificación para omitir rutas de autenticación
        if (request.getRequestURI().equals("/login") || request.getRequestURI().startsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(authHeader != null){
            var token = authHeader.replace("Bearer ","");
            var subject = tokenService.getSubject(token);
            if(subject != null){
                System.out.println("Subject: " + subject);
                var usuario = usuarioRepository.findByCorreoElectronico(subject);
                System.out.println("Usuario: " + usuario);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

}
