package pl.ostrowski.loan.domain;

import java.math.BigDecimal;

public class LoanConstraints {

    public static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(500);
    public static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(10_000);

    public static final String MIN_DUE_DATE = "";
    public static final String MAX_DUE_DATE = "";

    public static final double PRINCIPAL = 0.1;
}
