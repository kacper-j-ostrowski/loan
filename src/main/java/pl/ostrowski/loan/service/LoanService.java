package pl.ostrowski.loan.service;

import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;
import pl.ostrowski.loan.exception.LoanNotFound;
import pl.ostrowski.loan.exception.LoanValidationException;

public interface LoanService {

    LoanDto extendLoanByDefaultPeriod(Long loanId) throws LoanExtensionValidationException, LoanNotFound;

    LoanDto applyForLoan(LoanDto loan) throws LoanValidationException;
}
