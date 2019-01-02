package pl.ostrowski.loan.service;

import pl.ostrowski.loan.domain.Loan;

import java.math.BigDecimal;

public interface PrincipalCalculatorService {

    BigDecimal calculatePrincipalForLoan(Loan loan);

    BigDecimal getPrincipalRate();
}
