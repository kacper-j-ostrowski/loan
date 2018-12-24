package pl.ostrowski.loan.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public class LoanAcceptedResponseDto implements LoanResponseDto {

    private Long loanId;

    private Date dueDate;

    private BigDecimal dueAmount;

    private BigDecimal principal;

    public LoanAcceptedResponseDto(Long loanId, Date dueDate, BigDecimal dueAmount, BigDecimal principal) {
        this.loanId = loanId;
        this.dueDate = dueDate;
        this.dueAmount = dueAmount;
        this.principal = principal;
    }

    @Override
    public String getStatusOfLoan() {
        return LoanResponseDto.ACCEPTED;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public BigDecimal getDueAmount() {
        return dueAmount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }
}
