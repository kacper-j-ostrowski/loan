package pl.ostrowski.loan.validators.loanapplication;

import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.validators.GenericValidation;
import pl.ostrowski.loan.validators.Validation;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import static pl.ostrowski.loan.domain.LoanConstraints.MAX_AMOUNT;
import static pl.ostrowski.loan.domain.LoanConstraints.MIN_AMOUNT;

public class LoanValidationRules {
    public static final Validation<Loan> withinAllowedAmountRange = GenericValidation.from(loan ->
        loan.getAmount().compareTo(MIN_AMOUNT) >= 0 &&
                loan.getAmount().compareTo(MAX_AMOUNT) <= 0
    );

    public static final Validation<Loan> withinAllowedDatesRange = GenericValidation.from(loan ->
        !loan.getStartDate().before(new Date())
    );

    public static final Validation<Loan> notBetweenMidnightAndSixMorningWithMaximumAmount = GenericValidation.from(loan ->
        !(loan.getAmount().compareTo(MAX_AMOUNT) == 0 &&
                LocalTime.now().isAfter(LocalTime.MIDNIGHT) &&
                    LocalTime.now().isBefore(LocalTime.MIDNIGHT.plusHours(6)))
    );
}
