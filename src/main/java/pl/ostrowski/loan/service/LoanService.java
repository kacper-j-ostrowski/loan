package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.domain.PrincipalCalculator;
import pl.ostrowski.loan.repository.LoanRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    private PrincipalCalculator principalCalculator;

    @Autowired
    public LoanService(LoanRepository loanRepository, PrincipalCalculator principalCalculator) {
        this.loanRepository = loanRepository;
        this.principalCalculator = principalCalculator;
    }

    public Long saveLoan(Loan loan) {
        Loan insertedLoan = loanRepository.save(loan);
        return insertedLoan.getId();
    }

    public void calculateDueAmountForLoan(Loan loan) {
        BigDecimal dueAmount = principalCalculator.calculatePrincipalForLoan(loan);
        loan.setPrincipal(principalCalculator.getPrincipalRate());
        loan.setDueAmount(dueAmount);
    }

    public Optional<Loan> extendLoanByDefaultPeriod(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        loan.ifPresent(l -> l.setExtensionCounter(l.getExtensionCounter() + 1));
        //loan.setDueDate(loan.getDueDate().toLocalDateTime().plusDays(10));
        return loan;
    }
}
