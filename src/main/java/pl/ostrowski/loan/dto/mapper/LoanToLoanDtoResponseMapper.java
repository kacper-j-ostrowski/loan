package pl.ostrowski.loan.dto.mapper;

import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanAcceptedResponseDto;

public class LoanToLoanDtoResponseMapper {

    public static LoanAcceptedResponseDto mapToAcceptedResponse(Loan loan) {
        return LoanAcceptedResponseDto.builder()
                .loanId(loan.getId())
                .dueAmount(loan.getDueAmount())
                .dueDate(loan.getDueDate())
                .principal(loan.getPrincipal())
                .build();
    }
}
