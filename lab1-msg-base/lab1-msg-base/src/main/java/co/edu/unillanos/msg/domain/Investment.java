package co.edu.unillanos.msg.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Investment {

    private final String investmentId;
    private final String description;
    private final BigDecimal annualExpectedReturn;

    public Investment(String investmentId, String description, BigDecimal annualExpectedReturn) {
        this.investmentId = Objects.requireNonNull(investmentId);
        this.description = Objects.requireNonNull(description);
        this.annualExpectedReturn = Objects.requireNonNull(annualExpectedReturn);
    }

    public String investmentId() {
        return investmentId;
    }

    public String description() {
        return description;
    }

    public BigDecimal annualExpectedReturn() {
        return annualExpectedReturn;
    }

    /** RN6: rendimiento anual esperado / 52, en dólares con 2 decimales. */
    public BigDecimal weeklyReturn() {
        throw new UnsupportedOperationException("TODO");
    }
}
