package pl.ostrowski.loan.dto.mapper;

import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanRequestDto;

public class LoanRequestDtoMapper {

    public static Loan fromRequestDto(LoanRequestDto loanRequestDto) {
        return Loan.builder()
                .amount(loanRequestDto.getAmount())
                .startDate(loanRequestDto.getStartDate())
                .build();
    }
}
