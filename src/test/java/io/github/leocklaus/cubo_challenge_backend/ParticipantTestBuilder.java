package io.github.leocklaus.cubo_challenge_backend;

import io.github.leocklaus.cubo_challenge_backend.domain.entity.Participant;

public class ParticipantTestBuilder {
    private Long id = 1L;
    private String firstName = "Name";
    private String lastName = "Surname";
    private Double percentage = 25.0;

    public ParticipantTestBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public ParticipantTestBuilder withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public ParticipantTestBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public ParticipantTestBuilder withPercentage(Double percentage){
        this.percentage = percentage;
        return this;
    }

    public Participant build(){
        return new Participant(id, firstName, lastName, percentage);
    }
}
