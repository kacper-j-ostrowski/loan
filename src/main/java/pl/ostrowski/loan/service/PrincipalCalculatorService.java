package pl.ostrowski.loan.service;

import org.springframework.stereotype.Service;
import pl.ostrowski.loan.dto.LoanDto;

import java.math.BigDecimal;

import static pl.ostrowski.loan.domain.LoanConstraints.PRINCIPAL;

@Service
public class PrincipalCalculatorService {

    public BigDecimal calculatePrincipalForLoan(LoanDto loan) {
        return loan.getAmount().multiply(BigDecimal.ONE.add(BigDecimal.valueOf(PRINCIPAL)));
    }

    public BigDecimal getPrincipalRate() {
        return BigDecimal.valueOf(PRINCIPAL);
    }
}
