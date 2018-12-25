package pl.ostrowski.loan.domain;

import java.math.BigDecimal;

public class LoanConstraints {

    public static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(500);
    public static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(10_000);

    public static final String MIN_DUE_DATE = "";
    public static final String MAX_DUE_DATE = "2019-12-31";

    public static final double PRINCIPAL = 0.1;

    private static final int EXTENSION_PERIOD_IN_DAYS = 10;
}
