package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.dto.LoanRequestDto;
import pl.ostrowski.loan.dto.mapper.LoanDtoMapper;
import pl.ostrowski.loan.dto.mapper.LoanRequestDtoMapper;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;
import pl.ostrowski.loan.exception.LoanNotFound;
import pl.ostrowski.loan.exception.LoanValidationException;
import pl.ostrowski.loan.repository.LoanRepository;
import pl.ostrowski.loan.validators.loanapplication.LoanValidator;
import pl.ostrowski.loan.validators.loanextension.LoanExtensionValidator;

import java.time.LocalDate;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;

    private PrincipalCalculatorService principalCalculatorService;

    private LoanValidator loanValidator;

    private SystemParametersService systemParametersService;

    private LoanExtensionValidator loanExtensionValidator;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository, PrincipalCalculatorServiceImpl principalCalculatorService,
                           LoanValidator loanValidator, SystemParametersService systemParametersService,
                           LoanExtensionValidator loanExtensionValidator) {
        this.loanRepository = loanRepository;
        this.principalCalculatorService = principalCalculatorService;
        this.loanValidator = loanValidator;
        this.systemParametersService = systemParametersService;
        this.loanExtensionValidator = loanExtensionValidator;
    }

    public LoanDto extendLoanByDefaultPeriod(Long loanId) throws LoanExtensionValidationException, LoanNotFound {
        LoanDto loan = loanRepository.findById(loanId)
            .map(LoanDtoMapper::fromLoan)
            .orElseThrow(() -> new LoanNotFound(loanId));

        loanExtensionValidator.validate(loan);

        loan.setDueDate(loan.getDueDate().plusDays(systemParametersService.getExtensionPeriodInDays()));
        loan.setNumberOfExtensions(loan.getNumberOfExtensions() + 1);
        Loan extendedLoan = LoanDtoMapper.fromLoanDto(loan);
        loanRepository.save(extendedLoan);
        return loan;
    }

    public LoanDto applyForLoan(LoanRequestDto loanRequestDto) throws LoanValidationException {
        loanValidator.validate(loanRequestDto);
        Loan loan = LoanRequestDtoMapper.fromRequestDto(loanRequestDto);
        calculateDatesForLoan(loan, loanRequestDto.getDaysToRepayment());
        calculateDueAmountForLoan(loan);
        loan = loanRepository.save(loan);
        return LoanDtoMapper.fromLoan(loan);
    }

    private void calculateDatesForLoan(Loan loan, int daysToRepayment) {
        loan.setStartDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(daysToRepayment));
    }

    private void calculateDueAmountForLoan(Loan loan) {
        loan.setPrincipal(principalCalculatorService.getPrincipalRate());
        loan.setDueAmount(principalCalculatorService.calculatePrincipalForLoan(loan));
    }
}
