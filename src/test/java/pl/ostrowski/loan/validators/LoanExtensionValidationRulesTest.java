package pl.ostrowski.loan.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.validators.loanextension.ExtensionValidatonRules;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanExtensionValidationRulesTest {

    @Autowired
    private ExtensionValidatonRules extensionValidatonRules;

    @Test
    public void test_isBeforeDueDate_Valid() {
        LoanDto testDto = LoanDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .dueDate(LocalDate.parse("2100-12-12"))
                .build();
        boolean valid = extensionValidatonRules.isBeforeDueDate.test(testDto).isValid();
        assertTrue(valid);
    }

    @Test
    public void test_isBeforeDueDate_Invalid() {
        LoanDto testDto = LoanDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .dueDate(LocalDate.parse("2016-12-12"))
                .build();
        boolean valid = extensionValidatonRules.isBeforeDueDate.test(testDto).isValid();
        assertFalse(valid);
    }
}
