package pl.ostrowski.loan.integrationtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                .andExpect(status().isOk());
    }

}
