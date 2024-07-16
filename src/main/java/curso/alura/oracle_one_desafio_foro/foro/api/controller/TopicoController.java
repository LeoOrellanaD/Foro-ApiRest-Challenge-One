package curso.alura.oracle_one_desafio_foro.foro.api.controller;

import curso.alura.oracle_one_desafio_foro.foro.api.domain.Topico.DatosActualizarTopico;
import curso.alura.oracle_one_desafio_foro.foro.api.domain.Topico.DatosAgregarTopico;
import curso.alura.oracle_one_desafio_foro.foro.api.domain.Topico.DatosRespuestaTopico;

import curso.alura.oracle_one_desafio_foro.foro.api.domain.Topico.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> addTopico(@Valid @RequestBody DatosAgregarTopico datosAgregarTopico){
        DatosRespuestaTopico topico = topicoService.addTopico(datosAgregarTopico);
        return ResponseEntity.status(HttpStatus.CREATED).body(topico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listarTopicos(@PageableDefault(sort = "fechaDeCreacion", direction = org.springframework.data.domain.Sort.Direction.ASC, size = 10) Pageable paginacion) {
        var topicos = topicoService.listarTopicos(paginacion);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopicoPorId(@PathVariable Long id) {
        DatosRespuestaTopico topico = topicoService.getTopicoByID(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> updateTopico(@PathVariable Long id, @Valid @RequestBody DatosActualizarTopico datosActualizarTopico){
        if (!id.equals(datosActualizarTopico.id())) {
            return ResponseEntity.badRequest().body(null);
        }
        DatosRespuestaTopico topico = topicoService.updateTopico(id, datosActualizarTopico);
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {
        topicoService.deleteTopico(id);
        return ResponseEntity.noContent().build();
    }
}
