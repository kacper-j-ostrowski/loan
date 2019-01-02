package pl.ostrowski.loan.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LoanExtendedResponse implements LoanResponse {

    private int numberOfExtensions;

    private LocalDate newDueDate;

    public LoanExtendedResponse(int numberOfExtensions, LocalDate newDueDate) {
        this.numberOfExtensions = numberOfExtensions;
        this.newDueDate = newDueDate;
    }

    @Override
    public String getStatusOfLoan() {
        return LoanResponseCodes.EXTENDED;
    }
}
