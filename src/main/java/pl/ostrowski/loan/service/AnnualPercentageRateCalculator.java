package pl.ostrowski.loan.service;

import pl.ostrowski.loan.dto.LoanDto;

import java.math.BigDecimal;

public class AnnualPercentageRateCalculator implements PrincipalCalculatorService {
    
    @Override
    public BigDecimal calculatePrincipalForLoan(LoanDto loan) {
        return null;
    }

    @Override
    public BigDecimal getPrincipalRate() {
        return null;
    }
}
