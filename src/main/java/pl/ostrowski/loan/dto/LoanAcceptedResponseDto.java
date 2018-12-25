package pl.ostrowski.loan.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class LoanAcceptedResponseDto implements LoanResponseDto {

    private Long loanId;

    private LocalDate dueDate;

    private BigDecimal dueAmount;

    private BigDecimal principal;

    @Override
    public String getStatusOfLoan() {
        return LoanResponseDto.ACCEPTED;
    }
}
