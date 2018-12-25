package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.repository.LoanRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    private PrincipalCalculatorService principalCalculatorService;

    @Autowired
    public LoanService(LoanRepository loanRepository, PrincipalCalculatorService principalCalculatorService) {
        this.loanRepository = loanRepository;
        this.principalCalculatorService = principalCalculatorService;
    }

    public Long saveLoan(Loan loan) {
        Loan insertedLoan = loanRepository.save(loan);
        return insertedLoan.getId();
    }

    public void calculateDueAmountForLoan(LoanDto loan) {
        BigDecimal dueAmount = principalCalculatorService.calculatePrincipalForLoan(loan);
        loan.setPrincipal(principalCalculatorService.getPrincipalRate());
        loan.setDueAmount(dueAmount);
    }

    public Optional<Loan> extendLoanByDefaultPeriod(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        loan.ifPresent(l -> {
            l.setExtensionCounter(l.getExtensionCounter() + 1);
            l.setDueDate(l.getDueDate().plusDays(10));
        });
        return loan;
    }
}
