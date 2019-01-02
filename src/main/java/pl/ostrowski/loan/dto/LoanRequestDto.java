package pl.ostrowski.loan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanRequestDto {
    @NotNull
    private Integer daysToRepayment;

    @NotNull
    private BigDecimal amount;

    private LocalDate startDate = LocalDate.now();

    private LocalTime timeOfRequest = LocalTime.now();
}
