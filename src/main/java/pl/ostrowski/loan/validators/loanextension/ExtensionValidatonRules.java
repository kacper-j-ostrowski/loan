package pl.ostrowski.loan.validators.loanextension;

import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.validators.GenericValidation;
import pl.ostrowski.loan.validators.Validation;

import java.time.LocalDate;


public class ExtensionValidatonRules {

    public static final Validation<LoanDto> isBeforeDueDate = GenericValidation.from(loan ->
        !loan.getDueDate().isBefore(LocalDate.now())
    );
}
