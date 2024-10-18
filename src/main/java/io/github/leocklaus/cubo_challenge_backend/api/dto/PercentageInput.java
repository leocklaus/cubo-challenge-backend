package io.github.leocklaus.cubo_challenge_backend.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PercentageInput(
        @NotNull(message = "Valor de participação é obrigatório")
        @Positive(message = "Valor de participação deve ser maior que 0")
        @Max(value = 100, message = "Valor de participação não pode ser mais que 100%")
        Double newPercentage
) {
}
