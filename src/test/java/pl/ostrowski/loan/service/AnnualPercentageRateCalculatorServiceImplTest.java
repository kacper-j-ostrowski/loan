package pl.ostrowski.loan.service;

import org.junit.Test;
import pl.ostrowski.loan.dto.LoanDto;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AnnualPercentageRateCalculatorServiceImplTest {

    private AnnualPercentageRateCalculatorServiceImpl annualPercentageRateCalculatorService = new AnnualPercentageRateCalculatorServiceImpl();

    @Test
    public void test_calculateAnnualPercentageRate_SimpleCase() {

        LoanDto loan = LoanDto.builder()
                .amount(BigDecimal.valueOf(500))
                .amount(BigDecimal.valueOf(510))
                .daysToRepayment(25)
                .build();

        BigDecimal result = annualPercentageRateCalculatorService.calculate(loan);

        assertEquals(0, BigDecimal.valueOf(33.5).compareTo(result));
    }
}
