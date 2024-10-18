package io.github.leocklaus.cubo_challenge_backend.api.controller;

import io.github.leocklaus.cubo_challenge_backend.api.dto.ParticipantInput;
import io.github.leocklaus.cubo_challenge_backend.api.dto.PercentageInput;
import io.github.leocklaus.cubo_challenge_backend.domain.entity.Participant;
import io.github.leocklaus.cubo_challenge_backend.domain.service.DataService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public ResponseEntity<List<Participant>> getAllParticipants(){
        List<Participant> participants = dataService.getAllParticipants();
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/participant/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id){
        Participant participant = dataService.getParticipantById(id);
        return ResponseEntity.ok(participant);
    }

    @PostMapping("/participant")
    public ResponseEntity<Void> addParticipant(@RequestBody @Valid ParticipantInput input){
        Participant participant = dataService.addParticipant(input);
        var uri = createURI(participant.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/participant/{id}")
    public ResponseEntity<Void> updateParticipantPercentage(
            @PathVariable Long id,
            @RequestBody @Valid PercentageInput percentageInput
            ){

        dataService.updateParticipantPercentage(
                id,
                percentageInput.newPercentage()
        );
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/participant/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id){
        dataService.removeParticipant(id);
        return ResponseEntity.noContent().build();
    }

    private URI createURI(Long id){
        return URI.create("/data/participant/" + id);
    }
}
