package pl.ostrowski.loan.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ostrowski.loan.domain.Loan;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.dto.LoanRequestDto;
import pl.ostrowski.loan.dto.mapper.LoanRequestDtoMapper;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;
import pl.ostrowski.loan.exception.LoanNotFound;
import pl.ostrowski.loan.exception.LoanValidationException;
import pl.ostrowski.loan.repository.LoanRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanServiceImplTest {

    @Autowired
    private LoanServiceImpl loanServiceImpl;

    @MockBean
    private LoanRepository loanRepository;

    @Test
    public void test_applyForLoan_simpleCase() throws LoanValidationException {
        //given
        LoanRequestDto testDto = LoanRequestDto.builder()
                            .amount(BigDecimal.valueOf(7_000))
                            .daysToRepayment(30)
                            .build();

        Loan loanToReturn = LoanRequestDtoMapper.fromRequestDto(testDto);
        loanToReturn.setPrincipal(BigDecimal.valueOf(0.1));
        loanToReturn.setDueAmount(BigDecimal.valueOf(7_700));
        loanToReturn.setStartDate(LocalDate.now());
        loanToReturn.setDueDate(LocalDate.now().plusDays(30));
        loanToReturn.setId(1L);

        when(loanRepository.save(any(Loan.class))).thenReturn(loanToReturn);

        //when
        LoanDto createdLoanDto = loanServiceImpl.applyForLoan(testDto);

        //then
        assertEquals(0, createdLoanDto.getDueAmount().compareTo(BigDecimal.valueOf(7_700)));
        assertEquals(0, createdLoanDto.getPrincipal().compareTo(BigDecimal.valueOf(0.1)));
        assertEquals(LocalDate.now().plusDays(30), createdLoanDto.getDueDate());
    }


    @Test(expected = LoanValidationException.class)
    public void test_applyForLoan_rejectAmount() throws LoanValidationException {
        //given
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(100_000))
                .daysToRepayment(30)
                .build();

        //when
        loanServiceImpl.applyForLoan(testDto);
    }

    @Test(expected = LoanValidationException.class)
    public void test_applyForLoan_rejectDate() throws LoanValidationException {
        //given
        LoanRequestDto testDto = LoanRequestDto.builder()
                .amount(BigDecimal.valueOf(501))
                .daysToRepayment(366_000)
                .build();

        //when
        loanServiceImpl.applyForLoan(testDto);
    }

    @Test(expected = LoanNotFound.class)
    public void test_extendLoanByDefaultPeriod_notFoundException() throws LoanNotFound, LoanExtensionValidationException {
        loanServiceImpl.extendLoanByDefaultPeriod(1L);
    }

    @Test
    public void test_extendLoanByDefaultPeriod_simpleCase() throws LoanNotFound, LoanExtensionValidationException {
        //given
        Loan loanToReturn = new Loan();
        loanToReturn.setAmount(BigDecimal.valueOf(7_000));
        loanToReturn.setPrincipal(BigDecimal.valueOf(0.1));
        loanToReturn.setDueAmount(BigDecimal.valueOf(7_700));
        loanToReturn.setStartDate(LocalDate.now());
        loanToReturn.setDueDate(LocalDate.now().plusDays(30));
        loanToReturn.setId(1L);

        when(loanRepository.findById(any(Long.class))).thenReturn(Optional.of(loanToReturn));

        //when
        LoanDto returnedDto = loanServiceImpl.extendLoanByDefaultPeriod(1L);

        //then
        assertEquals(loanToReturn.getDueDate().plusDays(10), returnedDto.getDueDate());
    }

    @Test(expected = LoanExtensionValidationException.class)
    public void test_extendLoanByDefaultPeriod_afterLoanDueDate() throws LoanNotFound, LoanExtensionValidationException {
        //given
        Loan loanToReturn = new Loan();
        loanToReturn.setAmount(BigDecimal.valueOf(7_000));
        loanToReturn.setPrincipal(BigDecimal.valueOf(0.1));
        loanToReturn.setDueAmount(BigDecimal.valueOf(7_700));
        loanToReturn.setStartDate(LocalDate.now().minusDays(30));
        loanToReturn.setDueDate(LocalDate.now().minusDays(10));
        loanToReturn.setId(1L);

        when(loanRepository.findById(any(Long.class))).thenReturn(Optional.of(loanToReturn));

        //when
        loanServiceImpl.extendLoanByDefaultPeriod(1L);
    }
}
