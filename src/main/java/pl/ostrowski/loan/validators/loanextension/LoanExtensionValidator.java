package pl.ostrowski.loan.validators.loanextension;

import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;

public class LoanExtensionValidator {

    public static void validate(LoanDto loan) throws LoanExtensionValidationException {

        StringBuilder validationMessages = new StringBuilder();

        validationMessages.append(ExtensionValidatonRules.isBeforeDueDate.test(loan)
                .getValidationMessageIfInvalid("")
                .orElse(""));

        if(validationMessages.length() > 0) {
            throw new LoanExtensionValidationException(validationMessages.toString());
        }
    }
}
