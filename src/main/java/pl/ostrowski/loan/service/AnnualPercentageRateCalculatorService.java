package pl.ostrowski.loan.service;

import pl.ostrowski.loan.dto.LoanDto;

import java.math.BigDecimal;

public interface AnnualPercentageRateCalculatorService {

    BigDecimal calculate(LoanDto loan);
}
