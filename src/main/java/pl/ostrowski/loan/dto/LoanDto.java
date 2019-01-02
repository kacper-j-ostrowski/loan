package pl.ostrowski.loan.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanDto {

    private Long id;

    @NotNull
    private Integer daysToRepayment;

    private LocalDate startDate;

    private LocalDate dueDate;

    @NotNull
    private BigDecimal amount;

    private BigDecimal dueAmount;

    private BigDecimal principal;

    private int numberOfExtensions;
}
