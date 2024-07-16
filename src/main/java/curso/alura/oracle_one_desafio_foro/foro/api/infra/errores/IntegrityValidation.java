package curso.alura.oracle_one_desafio_foro.foro.api.infra.errores;

public class IntegrityValidation extends RuntimeException  {

    public IntegrityValidation(String s) {
        super(s);
    }
}
