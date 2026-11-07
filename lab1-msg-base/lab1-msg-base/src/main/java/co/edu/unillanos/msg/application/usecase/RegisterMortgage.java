
package co.edu.unillanos.msg.application.usecase;

import java.util.Objects;

import co.edu.unillanos.msg.application.port.MortgageRepository;
import co.edu.unillanos.msg.domain.Mortgage;

public final class RegisterMortgage {

    private final MortgageRepository mortgages;

    public RegisterMortgage(MortgageRepository mortgages) {
        this.mortgages = Objects.requireNonNull(mortgages);
    }

    public Mortgage execute(RegisterMortgageRequest request) {
        Objects.requireNonNull(request);
        if (mortgages.findByNumber(request.mortgageNumber()).isPresent()) {
            throw new IllegalStateException("Ya existe la hipoteca " + request.mortgageNumber());
        }
        Mortgage mortgage = new Mortgage(
                request.mortgageNumber(),
                request.weeklyPrincipalAndInterest(),
                request.annualPropertyTax(),
                request.annualInsurancePremium(),
                request.coupleWeeklyIncome());
        mortgages.save(mortgage);
        return mortgage;
    }
}
