package curso.alura.oracle_one_desafio_foro.foro.api.infra.errores;

public class DuplicationValidation extends RuntimeException{

    public DuplicationValidation(String s) {
        super(s);
    }
}
