package pl.ostrowski.loan.validators.loanapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.ostrowski.loan.dto.LoanRequestDto;
import pl.ostrowski.loan.validators.GenericValidation;
import pl.ostrowski.loan.validators.Validation;

import java.time.LocalDate;
import java.time.LocalTime;

import pl.ostrowski.loan.service.SystemParametersService;

@Component
public class LoanValidationRules {

    @Autowired
    private SystemParametersService systemParametersService;

    public final Validation<LoanRequestDto> withinAllowedAmountRange = GenericValidation.from(loan ->
        loan.getAmount().compareTo(systemParametersService.getMinAmount()) >= 0 &&
                loan.getAmount().compareTo(systemParametersService.getMaxAmount()) <= 0
    );

    public final Validation<LoanRequestDto> withinAllowedDatesRange = GenericValidation.from(loan ->
        !LocalDate.now().plusDays(loan.getDaysToRepayment())
                .isAfter(LocalDate.parse(systemParametersService.getMaxDueDate()))
    );

    public final Validation<LoanRequestDto> notBetweenMidnightAndSixMorningWithMaximumAmount = GenericValidation.from(loan ->
        !(loan.getAmount().compareTo(systemParametersService.getMaxAmount()) == 0 &&
                loan.getTimeOfRequest().isAfter(LocalTime.MIDNIGHT) &&
                    loan.getTimeOfRequest().isBefore(LocalTime.MIDNIGHT.plusHours(6)))
    );
}
