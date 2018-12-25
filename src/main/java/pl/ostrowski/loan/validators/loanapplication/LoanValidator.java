package pl.ostrowski.loan.validators.loanapplication;

import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.exception.LoanValidationException;

import static pl.ostrowski.loan.validators.loanapplication.LoanErrorMessages.*;

public class LoanValidator {
    public static void validate(LoanDto loan) throws LoanValidationException {

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

        if(validationMessages.length() > 0) {
            throw new LoanValidationException(validationMessages.toString());
        }
    }
}
