package io.github.leocklaus.cubo_challenge_backend.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ParticipantInput(
        @NotBlank(message = "O Nome é obrigatório")
        String firstName,
        @NotBlank(message = "O sobrenome é obrigatório")
        String lastName,
        @NotNull(message = "Valor de participação é obrigatório")
        @Positive(message = "Valor de participação deve ser maior que 0")
        @Max(value = 100, message = "Valor de participação não pode ser mais que 100%")
        Double percentage
) {
}
