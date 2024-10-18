package io.github.leocklaus.cubo_challenge_backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.leocklaus.cubo_challenge_backend.api.dto.ParticipantInput;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "tb_participant")
@Getter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Getter
    @Column(nullable = false)
    private Double percentage;

    public Participant(){}

    public Participant(String firstName, String lastName, Double percentage){
        this.firstName = firstName;
        this.lastName = lastName;
        this.percentage = percentage;
    }

    public Participant fromInput(ParticipantInput input){
        return new Participant(
                input.firstName(),
                input.lastName(),
                input.percentage()
        );
    }

    public void updatePercentage(Double newPercentage){
        this.percentage = newPercentage;
    }

    @JsonIgnore
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Participant that = (Participant) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
