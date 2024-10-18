package io.github.leocklaus.cubo_challenge_backend.domain.service;

import io.github.leocklaus.cubo_challenge_backend.api.dto.ParticipantInput;
import io.github.leocklaus.cubo_challenge_backend.domain.entity.Participant;
import io.github.leocklaus.cubo_challenge_backend.domain.exception.FullPercentageExceededException;
import io.github.leocklaus.cubo_challenge_backend.domain.exception.ParticipantAlreadyExistsException;
import io.github.leocklaus.cubo_challenge_backend.domain.exception.ParticipantNotFoundException;
import io.github.leocklaus.cubo_challenge_backend.domain.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private final ParticipantRepository repository;

    public DataService(ParticipantRepository repository) {
        this.repository = repository;
    }

    public List<Participant> getAllParticipants(){
        return repository.findAll();
    }

    public Participant getParticipantById(Long id){
        return getParticipantByIdOrThrowsException(id);
    }

    public Participant addParticipant(ParticipantInput input){

        var participant = new Participant()
                .fromInput(input);

        if(hasNotAvailablePercentage(participant.getPercentage())){
            throw new FullPercentageExceededException();
        }

        if(hasAlreadyBeenAdded(participant)){
            throw new ParticipantAlreadyExistsException(
                    participant.getFullName()
            );
        }

        return repository.save(participant);
    }

    public void updateParticipantPercentage(Long participantId, Double newPercentage){
        Participant participant = getParticipantByIdOrThrowsException(participantId);
        Double percentageDifference = newPercentage - participant.getPercentage();

        if(hasNotAvailablePercentage(percentageDifference)){
            throw new FullPercentageExceededException();
        }

        participant.updatePercentage(newPercentage);
        repository.save(participant);

    }

    public void removeParticipant(Long participantId){
        Participant participant = getParticipantByIdOrThrowsException(participantId);
        repository.delete(participant);
    }

    private Participant getParticipantByIdOrThrowsException(Long participantId){
        return repository.findById(participantId)
                .orElseThrow(()-> new ParticipantNotFoundException(participantId));
    }

    private boolean hasNotAvailablePercentage(Double participantPercentage){
        Double currentTotalPercentage = repository.getTotalPercentage();

        return currentTotalPercentage + participantPercentage > 100;
    }

    private boolean hasAlreadyBeenAdded(Participant participant){
        return repository.existsByFirstNameAndLastName(
                participant.getFirstName(),
                participant.getLastName()
        );
    }




}
