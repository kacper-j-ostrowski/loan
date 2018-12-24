package pl.ostrowski.loan.validators.loanapplication;

import pl.ostrowski.loan.domain.Loan;

import java.util.Optional;

import static pl.ostrowski.loan.validators.loanapplication.LoanErrorMessages.NOT_WITHIN_AMOUNT_RANGE;
import static pl.ostrowski.loan.validators.loanapplication.LoanErrorMessages.NOT_WITHIN_DATES_RANGE;

public class LoanValidator {
    public static Optional<String> validate(Loan loan) {

        StringBuilder validationMessages = new StringBuilder();

        validationMessages.append(LoanValidationRules.withinAllowedAmountRange.test(loan)
                .getFieldNameIfInvalid(NOT_WITHIN_AMOUNT_RANGE)
                .orElse(""));

        validationMessages.append(LoanValidationRules.getWithinAllowedDatesRange.test(loan)
                .getFieldNameIfInvalid(NOT_WITHIN_DATES_RANGE)
                .orElse(""));

        return validationMessages.length() == 0 ? Optional.empty() : Optional.of(validationMessages.toString());
    }
}
