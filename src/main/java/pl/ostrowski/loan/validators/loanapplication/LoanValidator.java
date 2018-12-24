package pl.ostrowski.loan.validators.loanapplication;

import pl.ostrowski.loan.domain.Loan;

import java.util.Optional;

import static pl.ostrowski.loan.validators.loanapplication.LoanErrorMessages.*;

public class LoanValidator {
    public static Optional<String> validate(Loan loan) {

        StringBuilder validationMessages = new StringBuilder();

        validationMessages.append(LoanValidationRules.withinAllowedAmountRange.test(loan)
                .getValidationMessageIfInvalid(NOT_WITHIN_AMOUNT_RANGE + "\n")
                .orElse(""));

        validationMessages.append(LoanValidationRules.withinAllowedDatesRange.test(loan)
                .getValidationMessageIfInvalid(NOT_WITHIN_DATES_RANGE  + "\n")
                .orElse(""));

        validationMessages.append(LoanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(loan)
                .getValidationMessageIfInvalid(BETWEEN_MIDNIGHT_AND_SIX_AM_ON_MAX_AMOUNT  + "\n")
                .orElse(""));

        return validationMessages.length() == 0 ? Optional.empty() : Optional.of(validationMessages.toString());
    }
}
