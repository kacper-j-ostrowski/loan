package pl.ostrowski.loan.dto.mapper;

import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanDto;

public class LoanDtoMapper {

    private LoanDtoMapper() {}

    public static LoanDto fromLoan(Loan loan) {
        return LoanDto.builder()
                .amount(loan.getAmount())
                .dueAmount(loan.getDueAmount())
                .startDate(loan.getStartDate())
                .dueDate(loan.getDueDate())
                .principal(loan.getPrincipal())
                .numberOfExtensions(loan.getExtensionCounter())
                .id(loan.getId())
                .build();
    }

    public static Loan fromLoanDto(LoanDto loanDto) {
        return Loan.builder()
                .amount(loanDto.getAmount())
                .dueAmount(loanDto.getDueAmount())
                .startDate(loanDto.getStartDate())
                .dueDate(loanDto.getDueDate())
                .id(loanDto.getId())
                .principal(loanDto.getPrincipal())
                .extensionCounter(loanDto.getNumberOfExtensions())
                .build();
    }
}
