package io.github.leocklaus.cubo_challenge_backend.domain.exception;

public class ParticipantAlreadyExistsException extends RuntimeException {
    public ParticipantAlreadyExistsException(String fullName) {
        super("Participação de " + fullName + " já adicionada. Use a ferramenta atualizar participação.");
    }
}
