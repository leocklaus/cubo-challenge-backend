package io.github.leocklaus.cubo_challenge_backend.domain.service;

import io.github.leocklaus.cubo_challenge_backend.ParticipantTestBuilder;
import io.github.leocklaus.cubo_challenge_backend.api.dto.ParticipantInput;
import io.github.leocklaus.cubo_challenge_backend.domain.entity.Participant;
import io.github.leocklaus.cubo_challenge_backend.domain.exception.FullPercentageExceededException;
import io.github.leocklaus.cubo_challenge_backend.domain.exception.ParticipantAlreadyExistsException;
import io.github.leocklaus.cubo_challenge_backend.domain.exception.ParticipantNotFoundException;
import io.github.leocklaus.cubo_challenge_backend.domain.repository.ParticipantRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataServiceTest {

    @Mock
    ParticipantRepository participantRepository;

    @Captor
    ArgumentCaptor<Participant> participantArgumentCaptor;

    @InjectMocks
    DataService dataService;

    @Nested
    class GetAllParticipants{
        @Test
        void shouldReturnAllParticipants() {
            var participant = new ParticipantTestBuilder()
                    .build();
            List<Participant> participants = List.of(participant);

            doReturn(participants).when(participantRepository).findAll();

            List<Participant> allParticipants = dataService.getAllParticipants();

            assertThat(allParticipants)
                    .isEqualTo(participants);
        }
    }

    @Nested
    class GetParticipantById {
        @Test
        void shouldReturnParticipantByIdIfExists() {
            Participant participant = new ParticipantTestBuilder()
                    .build();

            doReturn(Optional.of(participant)).when(participantRepository).findById(any());

            Participant result = dataService.getParticipantById(1L);

            assertThat(result)
                    .isEqualTo(participant);

        }

        @Test
        void shouldThrowExceptionIfParticipantNotFound(){
            doReturn(Optional.empty()).when(participantRepository).findById(any());

            assertThrows(ParticipantNotFoundException.class, ()->{
                dataService.getParticipantById(1L);
            });
        }
    }

    @Nested
    class addParticipant {

        @Test
        void shouldAddParticipant() {

            ParticipantInput participantInput = new ParticipantInput(
                    "Name",
                    "Surname",
                    25.0
            );

            Participant participant = new Participant()
                    .fromInput(participantInput);


            doReturn(0.0).when(participantRepository).getTotalPercentage();
            doReturn(false).when(participantRepository).existsByFirstNameAndLastName(any(), any());
            doReturn(participant).when(participantRepository).save(any());

            Participant response = dataService.addParticipant(participantInput);

            assertThat(response.getFirstName())
                    .isEqualTo(participantInput.firstName());

            assertThat(response.getLastName())
                    .isEqualTo(participantInput.lastName());

            assertThat(response.getPercentage())
                    .isEqualTo(participantInput.percentage());

        }

        @Test
        void shouldThrowExceptionIfFullPercentageExceeded() {
            ParticipantInput participantInput = new ParticipantInput(
                    "Name",
                    "Surname",
                    25.0
            );


            doReturn(100.0).when(participantRepository).getTotalPercentage();

            assertThrows(FullPercentageExceededException.class, ()->{
                dataService.addParticipant(participantInput);
            });
        }

        @Test
        void shouldThrowExceptionIfParticipantAlreadyExists(){
            ParticipantInput participantInput = new ParticipantInput(
                    "Name",
                    "Surname",
                    25.0
            );


            doReturn(0.0).when(participantRepository).getTotalPercentage();
            doReturn(true).when(participantRepository).existsByFirstNameAndLastName(
                    participantInput.firstName(), participantInput.lastName());

            assertThrows(ParticipantAlreadyExistsException.class, ()->{
                dataService.addParticipant(participantInput);
            });
        }
    }

    @Nested
    class updateParticipantPercentage {
        @Test
        void shouldUpdateParticipant(){
            Participant participant = new ParticipantTestBuilder()
                    .withPercentage(20.0)
                    .build();

            doReturn(Optional.of(participant)).when(participantRepository).findById(any());
            doReturn(0.0).when(participantRepository).getTotalPercentage();
            doReturn(participant).when(participantRepository).save(participantArgumentCaptor.capture());

            dataService.updateParticipantPercentage(1L, 50.0);

            assertThat(participantArgumentCaptor.getValue().getPercentage())
                    .isEqualTo(50.0);

        }

        @Test
        void shouldThrowExceptionIfFullPercentageExceeded(){
            Participant participant = new ParticipantTestBuilder()
                    .withPercentage(20.0)
                    .build();

            doReturn(Optional.of(participant)).when(participantRepository).findById(any());
            doReturn(100.0).when(participantRepository).getTotalPercentage();

            assertThrows(FullPercentageExceededException.class, ()-> {
               dataService.updateParticipantPercentage(1L, 70.0);
            });
        }
    }

    @Nested
    class removeParticipant {
        @Test
        void shouldRemoveParticipant() {
            Participant participant = new ParticipantTestBuilder()
                    .build();

            doReturn(Optional.of(participant)).when(participantRepository).findById(any());

            dataService.removeParticipant(1L);

            verify(participantRepository, times(1)).delete(any());
        }
        @Test
        void shouldThrowExceptionIfParticipantNotFound(){
            doReturn(Optional.empty()).when(participantRepository).findById(any());

            assertThrows(ParticipantNotFoundException.class, ()->{
                dataService.removeParticipant(1L);
            });
        }
    }
}