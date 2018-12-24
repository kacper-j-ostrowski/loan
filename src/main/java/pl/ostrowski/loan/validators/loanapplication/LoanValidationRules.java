package pl.ostrowski.loan.validators.loanapplication;

import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.validators.GenericValidation;
import pl.ostrowski.loan.validators.Validation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

public class LoanValidationRules {
    public static final Validation<Loan> withinAllowedAmountRange = GenericValidation.from(loan ->
        loan.getAmount().compareTo(BigDecimal.valueOf(500)) > 0 &&
                loan.getAmount().compareTo(BigDecimal.valueOf(10_000)) < 0
    );

    public static final Validation<Loan> getWithinAllowedDatesRange = GenericValidation.from(loan ->
        !loan.getStartDate().before(new Date()) && loan.getDueDate().before(Date.from(Instant.MAX))
    );
}
