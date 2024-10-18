package io.github.leocklaus.cubo_challenge_backend.domain.repository;

import io.github.leocklaus.cubo_challenge_backend.domain.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    @Query("SELECT COALESCE(SUM(p.percentage),0) FROM Participant p")
    Double getTotalPercentage();
}
