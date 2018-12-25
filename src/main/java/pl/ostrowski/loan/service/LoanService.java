package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.dto.mapper.LoanDtoMapper;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;
import pl.ostrowski.loan.exception.LoanNotFound;
import pl.ostrowski.loan.exception.LoanValidationException;
import pl.ostrowski.loan.repository.LoanRepository;
import pl.ostrowski.loan.validators.loanapplication.LoanValidator;
import pl.ostrowski.loan.validators.loanextension.LoanExtensionValidator;

import static pl.ostrowski.loan.domain.LoanConstraints.EXTENSION_PERIOD_IN_DAYS;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    private PrincipalCalculatorService principalCalculatorService;

    @Autowired
    public LoanService(LoanRepository loanRepository, PrincipalCalculatorService principalCalculatorService) {
        this.loanRepository = loanRepository;
        this.principalCalculatorService = principalCalculatorService;
    }

    public LoanDto extendLoanByDefaultPeriod(Long loanId) throws LoanExtensionValidationException, LoanNotFound {
        LoanDto loan = loanRepository.findById(loanId)
            .map(LoanDtoMapper::fromLoan)
            .orElseThrow(() -> new LoanNotFound(loanId));

        LoanExtensionValidator.validate(loan);

        loan.setDueDate(loan.getDueDate().plusDays(EXTENSION_PERIOD_IN_DAYS));
        loan.setNumberOfExtensions(loan.getNumberOfExtensions() + 1);
        Loan extendedLoan = LoanDtoMapper.fromLoanDto(loan);
        loanRepository.save(extendedLoan);
        return loan;
    }

    public LoanDto applyForLoan(LoanDto loan) throws LoanValidationException {
        LoanValidator.validate(loan);
        calculateDueAmountForLoan(loan);
        Loan newLoan = LoanDtoMapper.fromLoanDto(loan);
        newLoan = loanRepository.save(newLoan);
        return LoanDtoMapper.fromLoan(newLoan);
    }

    private void calculateDueAmountForLoan(LoanDto loan) {
        loan.setPrincipal(principalCalculatorService.getPrincipalRate());
        loan.setDueAmount(principalCalculatorService.calculatePrincipalForLoan(loan));
    }
}
