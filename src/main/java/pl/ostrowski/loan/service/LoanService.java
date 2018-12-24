package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.repository.LoanRepository;

import java.math.BigDecimal;

@Service
public class LoanService {

    private LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Long saveLoan(Loan loan) {
        loan.setDueAmount(loan.getAmount().multiply(BigDecimal.valueOf(1.1)));
        Loan insertedLoan = loanRepository.save(loan);
        return insertedLoan.getId();
    }
}
