package curso.alura.oracle_one_desafio_foro.foro.api.domain.Topico;


import curso.alura.oracle_one_desafio_foro.foro.api.domain.Curso.DatosCurso;
import curso.alura.oracle_one_desafio_foro.foro.api.domain.Usuario.DatosUsuario;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        String status,
        DatosUsuario autor,
        DatosCurso curso
){}
