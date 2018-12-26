package pl.ostrowski.loan.validators.loanapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.exception.LoanValidationException;

import static pl.ostrowski.loan.validators.loanapplication.LoanErrorMessages.*;

@Component
public class LoanValidator {

    @Autowired
    private LoanValidationRules loanValidationRules;

    public void validate(LoanDto loan) throws LoanValidationException {

        StringBuilder validationMessages = new StringBuilder();

        validationMessages.append(loanValidationRules.withinAllowedAmountRange.test(loan)
                .getValidationMessageIfInvalid(NOT_WITHIN_AMOUNT_RANGE + "\n")
                .orElse(""));

        validationMessages.append(loanValidationRules.withinAllowedDatesRange.test(loan)
                .getValidationMessageIfInvalid(NOT_WITHIN_DATES_RANGE  + "\n")
                .orElse(""));

        validationMessages.append(loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(loan)
                .getValidationMessageIfInvalid(BETWEEN_MIDNIGHT_AND_SIX_AM_ON_MAX_AMOUNT  + "\n")
                .orElse(""));

        if(validationMessages.length() > 0) {
            throw new LoanValidationException(validationMessages.toString());
        }
    }
}
