package pl.ostrowski.loan.domain;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static pl.ostrowski.loan.domain.LoanConstraints.PRINCIPAL;

@Component
public class PrincipalCalculator {

    public BigDecimal calculatePrincipalForLoan(Loan loan) {
        return loan.getAmount().multiply(BigDecimal.ONE.add(BigDecimal.valueOf(PRINCIPAL)));
    }

    public BigDecimal getPrincipalRate() {
        return BigDecimal.valueOf(PRINCIPAL);
    }
}
