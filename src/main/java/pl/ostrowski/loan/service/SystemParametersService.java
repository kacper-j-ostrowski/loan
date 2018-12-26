package pl.ostrowski.loan.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Setter
@Service
public class SystemParametersService {
    private BigDecimal minAmount = BigDecimal.valueOf(500);
    private BigDecimal maxAmount = BigDecimal.valueOf(10_000);
    private String maxDueDate = "2019-12-31";
    private double principal = 0.1;
    private int extensionPeriodInDays = 10;
}
