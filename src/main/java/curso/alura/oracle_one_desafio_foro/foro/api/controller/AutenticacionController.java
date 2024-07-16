package curso.alura.oracle_one_desafio_foro.foro.api.controller;

import curso.alura.oracle_one_desafio_foro.foro.api.domain.Usuario.DatosAutenticacionUsuario;


import curso.alura.oracle_one_desafio_foro.foro.api.domain.Usuario.Usuario;
import curso.alura.oracle_one_desafio_foro.foro.api.infra.security.DatosJWTToken;
import curso.alura.oracle_one_desafio_foro.foro.api.infra.security.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody DatosAutenticacionUsuario datosAutenticacionUsuario){
        System.out.println("Iniciando autenticación de usuario");
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.correoElectronico(), datosAutenticacionUsuario.contrasena());
        System.out.println(authToken);

        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        System.out.println(usuarioAutenticado);

        System.out.println("Usuario autenticado: " + usuarioAutenticado);


        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        System.out.println("Token generado: " + JWTtoken);


        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}