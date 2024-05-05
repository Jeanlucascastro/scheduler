package com.sociedade.scheduler.exception;

import lombok.Getter;

/**
 * Classe definida para ser a exception genérica,
 * onde todos os erros passarão por ela para ser tratados corremente.
 */

@Getter
public class GenericException extends RuntimeException {

    private String codigo;

    public GenericException(String codigo) {
        super("Exceção genérica com código: " + codigo);
        this.codigo = codigo;
    }

}
