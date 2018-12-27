package pl.ostrowski.loan.integrationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class HappyPathIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createLoan() throws Exception {
        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": \"7000\",\"daysToRepayment\": \"60\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusOfLoan", is("Accepted")))
                .andExpect(jsonPath("$.loan.amount", is(7000)))
                .andExpect(jsonPath("$.loan.dueAmount", is(7700.0)))
                .andExpect(jsonPath("$.loan.id", is(1)))
                .andExpect(jsonPath("$.loan.dueDate", is(LocalDate.now().plusDays(60).toString())))
                .andExpect(jsonPath("$.loan.startDate", is(LocalDate.now().toString())))
                .andExpect(jsonPath("$.loan.principal", is(0.1)))
                .andExpect(jsonPath("$.loan.numberOfExtensions", is(0)));
    }

}
