package io.github.leocklaus.cubo_challenge_backend.domain.entity;

import io.github.leocklaus.cubo_challenge_backend.api.dto.ParticipantInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantTest {

    private ParticipantInput participantInput;
    private Participant participant;

    @BeforeEach
    void setUp(){
        this.participantInput = new ParticipantInput(
                "Name",
                "Surname",
                25.0
        );
        this.participant = new Participant()
                .fromInput(participantInput);
    }

    @Test
    void shouldCreateParticipantFromInput(){
        Participant newParticipant = new Participant()
                .fromInput(participantInput);

        assertThat(newParticipant.getFirstName())
                .isEqualTo(participant.getFirstName());
        assertThat(newParticipant.getLastName())
                .isEqualTo(participant.getLastName());
        assertThat(newParticipant.getPercentage())
                .isEqualTo(participant.getPercentage());
    }

    @Test
    void shouldUpdatePercentage(){
        participant.updatePercentage(30.0);
        assertThat(participant.getPercentage())
                .isEqualTo(30.0);
    }

    @Test
    void shouldReturnFullName(){
        String expectedFullName = participant.getFirstName() + " " + participant.getLastName();
        String fullName = participant.getFullName();

        assertThat(fullName)
                .isEqualTo(expectedFullName);
    }

}