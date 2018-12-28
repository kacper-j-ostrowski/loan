package pl.ostrowski.loan.service;

import pl.ostrowski.loan.dto.LoanDto;

import java.math.BigDecimal;

public class AnnualPercentageRateCalculatorServiceImpl implements AnnualPercentageRateCalculatorService {

    @Override
    public BigDecimal calculate(LoanDto loan) {
         double apr = 100 * (Math.pow((loan.getDueAmount().divide(loan.getAmount())).doubleValue(),
                 365/loan.getDaysToRepayment().doubleValue()) - 1);

        return BigDecimal.valueOf(apr).setScale(1,BigDecimal.ROUND_HALF_UP);
    }
}
