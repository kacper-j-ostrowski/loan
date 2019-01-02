package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.Loan;

import java.math.BigDecimal;

@Service
public class PrincipalCalculatorServiceImpl implements PrincipalCalculatorService {

    @Autowired
    private SystemParametersService systemParametersService;

    public BigDecimal calculatePrincipalForLoan(Loan loan) {
        return loan.getAmount()
                .multiply(BigDecimal.ONE
                        .add(BigDecimal.valueOf(systemParametersService.getPrincipal())))
                            .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getPrincipalRate() {
        return BigDecimal.valueOf(systemParametersService.getPrincipal());
    }
}
