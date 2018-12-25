package pl.ostrowski.loan.response.mapper;

import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.response.LoanAcceptedResponse;

public class LoanToLoanResponseMapper {

    public static LoanAcceptedResponse mapToAcceptedResponse(LoanDto loan) {
        return LoanAcceptedResponse.builder()
                .loanId(loan.getId())
                .dueAmount(loan.getDueAmount())
                .dueDate(loan.getDueDate())
                .principal(loan.getPrincipal())
                .build();
    }
}
