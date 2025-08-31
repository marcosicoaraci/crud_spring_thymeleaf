package br.com.mrchagas.exceptions;

public abstract class AbstractCadastroApiExceptions extends RuntimeException{

    private final String[] args;

    public AbstractCadastroApiExceptions(String mensagem) {
        super(mensagem);
        this.args = null;
    }

    public AbstractCadastroApiExceptions(String message, String[] args) {
        super(message);
        this.args = args;
    }

    public AbstractCadastroApiExceptions(String message, Throwable cause) {
        super(message, cause);
        this.args = null;
    }

    public AbstractCadastroApiExceptions(String message, Throwable cause, String[] args) {
        super(message, cause);
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }
}
