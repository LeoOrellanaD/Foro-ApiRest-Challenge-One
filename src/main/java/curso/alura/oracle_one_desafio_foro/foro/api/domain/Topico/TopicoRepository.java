package curso.alura.oracle_one_desafio_foro.foro.api.domain.Topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
    Page<Topico> findAll(Pageable paginacion);
}
