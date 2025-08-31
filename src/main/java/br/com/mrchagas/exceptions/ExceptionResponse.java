package br.com.mrchagas.exceptions;

import java.util.Date;

public class ExceptionResponse {
    private Date timestamp;
    private String mensagem;
    private String detalhes;
    private String httpCodeMessage;

    public ExceptionResponse(Date timestamp, String mensagem, String detalhes,String httpCodeMessage) {
        super();
        this.timestamp = timestamp;
        this.mensagem = mensagem;
        this.detalhes = detalhes;
        this.httpCodeMessage=httpCodeMessage;
    }

    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMensaje() {
        return mensagem;
    }

    public String getDetalles() {
        return detalhes;
    }
}