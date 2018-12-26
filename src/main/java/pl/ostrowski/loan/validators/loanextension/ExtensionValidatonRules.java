package pl.ostrowski.loan.validators.loanextension;

import org.springframework.stereotype.Component;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.validators.GenericValidation;
import pl.ostrowski.loan.validators.Validation;

import java.time.LocalDate;

@Component
public class ExtensionValidatonRules {

    public final Validation<LoanDto> isBeforeDueDate = GenericValidation.from(loan ->
        !loan.getDueDate().isBefore(LocalDate.now())
    );
}
