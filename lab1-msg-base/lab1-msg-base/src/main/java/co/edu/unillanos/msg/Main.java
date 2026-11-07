package co.edu.unillanos.msg;

import java.math.BigDecimal;

import co.edu.unillanos.msg.application.usecase.EstimateFundsForWeek;
import co.edu.unillanos.msg.application.usecase.RegisterMortgage;
import co.edu.unillanos.msg.application.usecase.RegisterMortgageRequest;
import co.edu.unillanos.msg.domain.Investment;
import co.edu.unillanos.msg.domain.OperatingExpenses;
import co.edu.unillanos.msg.infrastructure.InMemoryInvestmentRepository;
import co.edu.unillanos.msg.infrastructure.InMemoryMortgageRepository;
import co.edu.unillanos.msg.infrastructure.InMemoryOperatingExpensesRepository;

/** Raíz de composición: conecta las capas. */
public final class Main {

    private static BigDecimal bd(String value) {
        return new BigDecimal(value);
    }

    public static void main(String[] args) {
        var mortgageRepository = new InMemoryMortgageRepository();
        var investmentRepository = new InMemoryInvestmentRepository();
        var expensesRepository = new InMemoryOperatingExpensesRepository(
                new OperatingExpenses(bd("39000.00")));

        var registerMortgage = new RegisterMortgage(mortgageRepository);
        registerMortgage.execute(new RegisterMortgageRequest("M-001",
                bd("300.00"), bd("2600.00"), bd("1040.00"), bd("1000.00")));
        registerMortgage.execute(new RegisterMortgageRequest("M-002",
                bd("200.00"), bd("1560.00"), bd("520.00"), bd("1000.00")));

        investmentRepository.save(new Investment("I-01", "Bonos del Tesoro", bd("52000.00")));
        investmentRepository.save(new Investment("I-02", "CDT", bd("26000.00")));

        var estimate = new EstimateFundsForWeek(
                mortgageRepository, investmentRepository, expensesRepository);
        System.out.println(estimate.execute().setScale(2).toPlainString());
    }
}
