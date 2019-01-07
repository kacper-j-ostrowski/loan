package pl.ostrowski.loan.validators;

import org.junit.Before;
import org.junit.Test;
import pl.ostrowski.loan.dto.LoanRequestDto;
import pl.ostrowski.loan.service.SystemParametersService;
import pl.ostrowski.loan.validators.loanapplication.LoanValidationRules;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanValidationRulesTest {

    private LoanValidationRules loanValidationRules;

    private SystemParametersService systemParametersService;

    @Before
    public void setConstraints() {
        systemParametersService = mock(SystemParametersService.class);

        when(systemParametersService.getMinAmount()).thenReturn(BigDecimal.valueOf(500));
        when(systemParametersService.getMaxAmount()).thenReturn(BigDecimal.valueOf(10_000));
        when(systemParametersService.getMaxDueDate()).thenReturn(LocalDate.now().plusYears(80).toString());

        loanValidationRules = new LoanValidationRules(systemParametersService);
    }


    @Test
    public void test_withinAllowedAmountRange_Valid() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .build();

        boolean valid = loanValidationRules.withinAllowedAmountRange.test(testDto).isValid();

        assertTrue(valid);
    }

    @Test
    public void test_withinAllowedAmountRange_InValid() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_001))
                .build();

        boolean valid = loanValidationRules.withinAllowedAmountRange.test(testDto).isValid();

        assertFalse(valid);
    }

    @Test
    public void test_withinAllowedAmountRange_ValidEdgeCases() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(500))
                .build();

        boolean valid = loanValidationRules.withinAllowedAmountRange.test(testDto).isValid();
        assertTrue(valid);

        testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_000))
                .build();

        valid = loanValidationRules.withinAllowedAmountRange.test(testDto).isValid();
        assertTrue(valid);
    }


    @Test
    public void test_withinAllowedDatesRange_Valid() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .daysToRepayment(50)
                .build();

        boolean valid = loanValidationRules.withinAllowedDatesRange.test(testDto).isValid();
        assertTrue(valid);
    }

    @Test
    public void test_withinAllowedDatesRange_InValid() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .daysToRepayment(366_000)
                .build();

        boolean valid = loanValidationRules.withinAllowedDatesRange.test(testDto).isValid();
        assertFalse(valid);
    }


    @Test
    public void test_withinAllowedDatesRange_ValidEdgeCase() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .daysToRepayment(50)
                .build();

        boolean valid = loanValidationRules.withinAllowedDatesRange.test(testDto).isValid();
        assertTrue(valid);
    }


    @Test
    public void test_notBetweenMidnightAndSixMorningWithMaximumAmount_Valid() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_000))
                .timeOfRequest(LocalTime.of(12,0))
                .build();

        boolean valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertTrue(valid);
    }

    @Test
    public void test_notBetweenMidnightAndSixMorningWithMaximumAmount_Invalid() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_000))
                .timeOfRequest(LocalTime.of(3,0))
                .build();

        boolean valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertFalse(valid);
    }

    @Test
    public void test_notBetweenMidnightAndSixMorningWithMaximumAmount_InvalidEdgeCases() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_000))
                .timeOfRequest(LocalTime.MIDNIGHT.plusMinutes(1))
                .build();

        boolean valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertFalse(valid);

        testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_000))
                .timeOfRequest(LocalTime.MIDNIGHT.plusHours(6).minusMinutes(1))
                .build();

        valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertFalse(valid);
    }

    @Test
    public void test_notBetweenMidnightAndSixMorningWithMaximumAmount_ValidEdgeCases() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_000))
                .timeOfRequest(LocalTime.MIDNIGHT.minusMinutes(1))
                .build();

        boolean valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertTrue(valid);

        testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(10_000))
                .startDate(LocalDate.now())
                .timeOfRequest(LocalTime.MIDNIGHT.plusHours(6))
                .build();

        valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertTrue(valid);
    }

    @Test
    public void test_notBetweenMidnightAndSixMorningWithMaximumAmount_ValidEdgeCases_WithAmountLessThanMax() {
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .timeOfRequest(LocalTime.MIDNIGHT.plusMinutes(1))
                .build();
        boolean valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertTrue(valid);

        testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .startDate(LocalDate.now())
                .timeOfRequest(LocalTime.MIDNIGHT.plusHours(6).minusMinutes(1))
                .build();

        valid = loanValidationRules.notBetweenMidnightAndSixMorningWithMaximumAmount.test(testDto).isValid();
        assertTrue(valid);
    }
}
