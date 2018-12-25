package pl.ostrowski.loan.response;

import lombok.Builder;
import lombok.Getter;
import pl.ostrowski.loan.dto.LoanDto;

@Getter
@Builder
public class LoanAcceptedResponse implements LoanResponse {

    private LoanDto loan;

    public LoanAcceptedResponse(LoanDto loan) {
        this.loan = loan;
    }

    @Override
    public String getStatusOfLoan() {
        return LoanResponse.ACCEPTED;
    }
}
