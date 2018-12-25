package pl.ostrowski.loan.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class LoanAcceptedResponse implements LoanResponse {

    private Long loanId;

    private LocalDate dueDate;

    private BigDecimal dueAmount;

    private BigDecimal principal;

    @Override
    public String getStatusOfLoan() {
        return LoanResponse.ACCEPTED;
    }
}
