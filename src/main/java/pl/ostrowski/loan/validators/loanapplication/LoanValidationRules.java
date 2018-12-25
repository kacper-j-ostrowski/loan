package pl.ostrowski.loan.validators.loanapplication;

import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.validators.GenericValidation;
import pl.ostrowski.loan.validators.Validation;

import java.time.LocalDate;
import java.time.LocalTime;

import static pl.ostrowski.loan.domain.LoanConstraints.*;

public class LoanValidationRules {
    public static final Validation<LoanDto> withinAllowedAmountRange = GenericValidation.from(loan ->
        loan.getAmount().compareTo(MIN_AMOUNT) >= 0 &&
                loan.getAmount().compareTo(MAX_AMOUNT) <= 0
    );

    public static final Validation<LoanDto> withinAllowedDatesRange = GenericValidation.from(loan ->
        !loan.getStartDate().isBefore(LocalDate.now()) && !loan.getDueDate().isAfter(LocalDate.parse(MAX_DUE_DATE))
    );

    public static final Validation<LoanDto> notBetweenMidnightAndSixMorningWithMaximumAmount = GenericValidation.from(loan ->
        !(loan.getAmount().compareTo(MAX_AMOUNT) == 0 &&
                LocalTime.now().isAfter(LocalTime.MIDNIGHT) &&
                    LocalTime.now().isBefore(LocalTime.MIDNIGHT.plusHours(6)))
    );
}
