package io.github.leocklaus.cubo_challenge_backend.domain.exception;

public class FullPercentageExceededException extends RuntimeException {
    public FullPercentageExceededException(){
        super("Total de 100% excedido com o valor de participação informado.");
    }
}
