package io.github.leocklaus.cubo_challenge_backend.domain.exception;

public class ParticipantNotFoundException extends RuntimeException{
    public ParticipantNotFoundException(Long id){
        super("Participant not found with id: " + id);
    }
}
