package pl.ostrowski.loan.service;

import pl.ostrowski.loan.dto.LoanDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;

public class AnnualPercentageRateCalculatorServiceImpl implements AnnualPercentageRateCalculatorService {

    @Override
    public BigDecimal calculate(LoanDto loan) {
        int daysInYear = IsoChronology.INSTANCE.isLeapYear(LocalDate.now().getYear()) ? 366 : 365;
        double apr = 100 * (Math.pow((loan.getDueAmount().divide(loan.getAmount())).doubleValue(),
                daysInYear/loan.getDaysToRepayment().doubleValue()) - 1);

        return BigDecimal.valueOf(apr).setScale(1,BigDecimal.ROUND_HALF_UP);
    }
}
