package io.github.leocklaus.cubo_challenge_backend.api.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {

    PARTICIPANT_NOT_FOUND("Participante não encontrado", "/participant-not-found"),
    INVALID_DATA("Dados inválidos", "/invalid-data"),
    RESOURCE_NOT_FOUND("Recurso não encontrado", "/resource-not-found"),
    FULL_PERCENTAGE_EXCEPTION("A participação excedeu 100%", "/full-percentage-exceeded"),
    PARTICIPANT_ALREADY_EXISTS("Participante já cadastrado", "/participant-already-exists");


    private final String title;
    private final String URI;

    ExceptionType(String title, String URI){
        this.title = title;
        this.URI = URI;
    }
}
