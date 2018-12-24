package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.domain.PrincipalCalculator;
import pl.ostrowski.loan.repository.LoanRepository;

import java.math.BigDecimal;

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
}
