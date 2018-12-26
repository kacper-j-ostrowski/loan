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
import org.springframework.web.context.WebApplicationContext;
import pl.ostrowski.loan.dto.LoanDto;
import pl.ostrowski.loan.exception.LoanExtensionValidationException;
import pl.ostrowski.loan.exception.LoanNotFound;
import pl.ostrowski.loan.exception.LoanValidationException;
import pl.ostrowski.loan.service.LoanService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private LoanController loanController;

    @MockBean
    private LoanService loanService;

    @Test
    public void test_postLoan_simpleCase() throws Exception {
        //given
        LoanDto dto = LoanDto.builder()
                .amount(BigDecimal.valueOf(7_000))
                .dueAmount(BigDecimal.valueOf(7_700))
                .build();
        when(loanService.applyForLoan(any(LoanDto.class))).thenReturn(dto);

        //when
        mockMvc.perform(post("/api/v1/loan")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"amount\": \"7000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isOk());
    }

    @Test
    public void test_postLoan_rejected() throws Exception {
        //given
        when(loanService.applyForLoan(any(LoanDto.class))).thenThrow(LoanValidationException.class);

        //when
        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": \"11000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isOk());
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

        when(loanService.extendLoanByDefaultPeriod(any(Long.class))).thenReturn(dto);

        //when
        mockMvc.perform(put("/api/v1/loan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": \"5000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isOk());
    }

    @Test
    public void test_extendLoan_loanNotFound() throws Exception {
        //given
        when(loanService.extendLoanByDefaultPeriod(any(Long.class))).thenThrow(LoanNotFound.class);

        //when
        mockMvc.perform(put("/api/v1/loan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": \"5000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_extendLoan_loanExtensionRejected() throws Exception {
        //given
        when(loanService.extendLoanByDefaultPeriod(any(Long.class))).thenThrow(LoanExtensionValidationException.class);

        //when
        mockMvc.perform(put("/api/v1/loan/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": \"5000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isBadRequest());
    }
}
