package pl.ostrowski.loan.validators.loanextension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;

@Component
public class LoanExtensionValidator {

    @Autowired
    private ExtensionValidatonRules extensionValidatonRules;

    public void validate(LoanDto loan) throws LoanExtensionValidationException {

        StringBuilder validationMessages = new StringBuilder();

        validationMessages.append(extensionValidatonRules.isBeforeDueDate.test(loan)
                .getValidationMessageIfInvalid("Cannot extended loan after due date is passed.\n")
                .orElse(""));

        if(validationMessages.length() > 0) {
            throw new LoanExtensionValidationException(validationMessages.toString());
        }
    }
}
