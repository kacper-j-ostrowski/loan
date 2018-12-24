package pl.ostrowski.loan.dto;

import java.math.BigDecimal;
import java.util.Date;

public class LoanAcceptedRepsoneDto implements LoanRepsoneDto {

    private Long loanId;

    private Date dueDate;

    private BigDecimal dueAmount;

    public LoanAcceptedRepsoneDto(Long loanId, Date dueDate, BigDecimal dueAmount) {
        this.loanId = loanId;
        this.dueDate = dueDate;
        this.dueAmount = dueAmount;
    }

    @Override
    public String getStatusOfLoan() {
        return LoanRepsoneDto.ACCEPTED;
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
}
