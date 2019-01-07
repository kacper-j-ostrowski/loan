package pl.ostrowski.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ostrowski.loan.domain.SystemParameter;
import pl.ostrowski.loan.repository.SystemParamsRepository;

import java.math.BigDecimal;

@Service
public class SystemParametersService {

    @Autowired
    private SystemParamsRepository systemParamsRepository;

    private static final String MIN_AMOUNT = "min_amount";
    private static final String MAX_AMOUNT = "max_amount";
    private static final String MAX_DUE_DATE = "max_due_date";
    private static final String PRINCIPAL = "principal";
    private static final String EXTENSION_DAYS = "extension_days";

    private static final String MISSING_REQUIRED_PARAMETERS = "Missing required parameters";

    public BigDecimal getMinAmount() {
        return systemParamsRepository.findById(MIN_AMOUNT)
                .map(sp -> BigDecimal.valueOf(Integer.valueOf(sp.getParamValue())))
                .orElseThrow(() -> new RuntimeException(MISSING_REQUIRED_PARAMETERS));
    }

    public BigDecimal getMaxAmount() {
        return systemParamsRepository.findById(MAX_AMOUNT)
                .map(sp -> BigDecimal.valueOf(Integer.valueOf(sp.getParamValue())))
                .orElseThrow(() -> new RuntimeException(MISSING_REQUIRED_PARAMETERS));
    }

    public String getMaxDueDate() {
        return systemParamsRepository.findById(MAX_DUE_DATE)
                .map(SystemParameter::getParamValue)
                .orElseThrow(() -> new RuntimeException(MISSING_REQUIRED_PARAMETERS));
    }

    public double getPrincipal() {
        return systemParamsRepository.findById(PRINCIPAL)
                .map(sp -> Double.parseDouble(sp.getParamValue()))
                .orElseThrow(() -> new RuntimeException(MISSING_REQUIRED_PARAMETERS));
    }

    public int getExtensionPeriodInDays() {
        return systemParamsRepository.findById(EXTENSION_DAYS)
                .map(sp -> Integer.parseInt(sp.getParamValue()))
                .orElseThrow(() -> new RuntimeException(MISSING_REQUIRED_PARAMETERS));
    }
}
