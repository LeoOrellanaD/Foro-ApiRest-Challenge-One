package curso.alura.oracle_one_desafio_foro.foro.api.domain.Topico;

import curso.alura.oracle_one_desafio_foro.foro.api.domain.Curso.CursoRepository;
import curso.alura.oracle_one_desafio_foro.foro.api.domain.Curso.DatosCurso;
import curso.alura.oracle_one_desafio_foro.foro.api.domain.Usuario.DatosUsuario;
import curso.alura.oracle_one_desafio_foro.foro.api.domain.Usuario.UsuarioRepository;
import curso.alura.oracle_one_desafio_foro.foro.api.infra.errores.DuplicationValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DatosRespuestaTopico addTopico(DatosAgregarTopico datosAgregarTopico){


        var autor = usuarioRepository.findById(datosAgregarTopico.autorId());
        if (autor.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        var curso = cursoRepository.findById(datosAgregarTopico.cursoId());
        if (curso.isEmpty()) {
            throw new IllegalArgumentException("Curso no encontrado");
        }

        if (topicoRepository.findByTituloAndMensaje(datosAgregarTopico.titulo(), datosAgregarTopico.mensaje()).isPresent()) {
            throw new DuplicationValidation("Tópico duplicado: el título y el mensaje ya existen");
        }


        var topico = new Topico(
                null,
                datosAgregarTopico.titulo(),
                datosAgregarTopico.mensaje(),
                LocalDateTime.now(),
                "ABIERTO",
                autor.get(),
                curso.get(),
                new ArrayList<>()
        );

        var savedTopico = topicoRepository.save(topico);

        var datosUsuario = new DatosUsuario(autor.get().getId(), autor.get().getNombre(), autor.get().getCorreoElectronico());
        var datosCurso = new DatosCurso(curso.get().getId(), curso.get().getNombre(), curso.get().getCategoria());

        return new DatosRespuestaTopico(
                savedTopico.getId(),
                savedTopico.getTitulo(),
                savedTopico.getMensaje(),
                savedTopico.getFechaDeCreacion(),
                savedTopico.getStatus(),
                datosUsuario,
                datosCurso
        );
    }

    public Page<DatosRespuestaTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion)
                .map(topico -> new DatosRespuestaTopico(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensaje(),
                        topico.getFechaDeCreacion(),
                        topico.getStatus(),
                        new DatosUsuario(topico.getAutor().getId(), topico.getAutor().getNombre(), topico.getAutor().getCorreoElectronico()),
                        new DatosCurso(topico.getCurso().getId(), topico.getCurso().getNombre(), topico.getCurso().getCategoria())
                ));
    }

    public DatosRespuestaTopico getTopicoByID(Long id){
        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            throw new EntityNotFoundException("topico no existente");
        }

        var datosUsuario = new DatosUsuario(topico.get().getAutor().getId(), topico.get().getAutor().getNombre(), topico.get().getAutor().getCorreoElectronico());
        var datosCurso = new DatosCurso(topico.get().getCurso().getId(), topico.get().getCurso().getNombre(), topico.get().getCurso().getCategoria());

        return new DatosRespuestaTopico(
                topico.get().getId(),
                topico.get().getTitulo(),
                topico.get().getMensaje(),
                topico.get().getFechaDeCreacion(),
                topico.get().getStatus(),
                datosUsuario,
                datosCurso
        );

    }

    public DatosRespuestaTopico updateTopico (Long id, DatosActualizarTopico datosActualizarTopico){
        System.out.println( id);
        System.out.println(datosActualizarTopico);
        var topicoOptional  = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            System.out.println("esta vacio");
            throw new EntityNotFoundException("Tópico no existente");
        }

        var topico = topicoOptional.get();

        if (datosActualizarTopico.titulo() != null && datosActualizarTopico.mensaje() != null) {
            var topicoExistente = topicoRepository.findByTituloAndMensaje(datosActualizarTopico.titulo(), datosActualizarTopico.mensaje());
            if (topicoExistente.isPresent() && !topicoExistente.get().getId().equals(id)) {
                throw new DuplicationValidation("Tópico duplicado: el título y el mensaje ya existen");
            }
        }

        if (datosActualizarTopico.titulo() != null) {
            topico.setTitulo(datosActualizarTopico.titulo());
        }
        if (datosActualizarTopico.mensaje() != null) {
            topico.setMensaje(datosActualizarTopico.mensaje());
        }
        if (datosActualizarTopico.status() != null) {
            topico.setStatus(datosActualizarTopico.status());
        }

        var updatedTopico = topicoRepository.save(topico);



        var datosUsuario = new DatosUsuario(topico.getAutor().getId(), topico.getAutor().getNombre(), topico.getAutor().getCorreoElectronico());
        var datosCurso = new DatosCurso(topico.getCurso().getId(), topico.getCurso().getNombre(), topico.getCurso().getCategoria());

        return new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus(),
                datosUsuario,
                datosCurso
        );
    }

    public void deleteTopico(Long id) {
        var topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isEmpty()) {
            System.out.println("no existe el topico");
            throw new EntityNotFoundException("Tópico no existente");
        }

        System.out.println("Eliminando");
        topicoRepository.deleteById(id);
    }
}
