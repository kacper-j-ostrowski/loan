package pl.ostrowski.loan.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.dto.LoanRequestDto;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;
import pl.ostrowski.loan.exception.LoanNotFound;
import pl.ostrowski.loan.exception.LoanValidationException;
import pl.ostrowski.loan.service.LoanServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTest {

    public static final String LOAN_APPLICATION_REQUEST = "{\"amount\": \"5000\",\"daysToRepayment\": \"60\"}";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private LoanServiceImpl loanServiceImpl;

    @Test
    public void test_postLoan_simpleCase() throws Exception {
        //given
        LoanDto dto = LoanDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .dueAmount(BigDecimal.valueOf(7_700))
                .id(1L)
                .build();
        when(loanServiceImpl.applyForLoan(any(LoanRequestDto.class))).thenReturn(dto);

        //when
        mockMvc.perform(post("/api/v1/loan")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"amount\": \"7000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusOfLoan", is("Accepted")))
                .andExpect(jsonPath("$.loan.amount", is(7000)))
                .andExpect(jsonPath("$.loan.dueAmount", is(7700)))
                .andExpect(jsonPath("$.loan.id", is(1)));
    }

    @Test
    public void test_postLoan_rejected() throws Exception {
        //given
        when(loanServiceImpl.applyForLoan(any(LoanRequestDto.class))).thenThrow(LoanValidationException.class);

        //when
        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": \"11000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusOfLoan", is("Rejected")));
    }

    @Test
    public void test_extendLoan_simpleCase() throws Exception {
        //given
        LoanDto dto = LoanDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .dueAmount(BigDecimal.valueOf(7_700))
                .numberOfExtensions(1)
                .dueDate(LocalDate.now().plusDays(60))
                .build();

        when(loanServiceImpl.extendLoanByDefaultPeriod(any(Long.class))).thenReturn(dto);

        //when
        mockMvc.perform(put("/api/v1/loan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LOAN_APPLICATION_REQUEST))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusOfLoan", is("Extended")))
                .andExpect(jsonPath("$.numberOfExtensions", is(1)));
    }

    @Test
    public void test_extendLoan_loanNotFound() throws Exception {
        //given
        when(loanServiceImpl.extendLoanByDefaultPeriod(any(Long.class))).thenThrow(LoanNotFound.class);

        //when
        mockMvc.perform(put("/api/v1/loan/1")
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_extendLoan_loanExtensionRejected() throws Exception {
        //given
        when(loanServiceImpl.extendLoanByDefaultPeriod(any(Long.class))).thenThrow(LoanExtensionValidationException.class);

        //when
        mockMvc.perform(put("/api/v1/loan/1")
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isBadRequest());
    }
}
