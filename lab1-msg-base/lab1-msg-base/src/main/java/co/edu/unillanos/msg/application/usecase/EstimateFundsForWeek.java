
package co.edu.unillanos.msg.application.usecase;

import java.math.BigDecimal;
import java.util.Objects;

import co.edu.unillanos.msg.application.port.InvestmentRepository;
import co.edu.unillanos.msg.application.port.MortgageRepository;
import co.edu.unillanos.msg.application.port.OperatingExpensesRepository;

/** Caso de uso: estimar los fondos disponibles para la semana. */
public final class EstimateFundsForWeek {

    private final MortgageRepository mortgages;
    private final InvestmentRepository investments;
    private final OperatingExpensesRepository expenses;

    public EstimateFundsForWeek(MortgageRepository mortgages,
                                InvestmentRepository investments,
                                OperatingExpensesRepository expenses) {
        this.mortgages = Objects.requireNonNull(mortgages);
        this.investments = Objects.requireNonNull(investments);
        this.expenses = Objects.requireNonNull(expenses);
    }

    public BigDecimal execute() {
        return totalInvestmentIncome()
                .add(totalCoupleContributions())
                .subtract(weeklyOperatingExpenses())
                .subtract(totalEscrowPaid());
    }

    /** Suma del rendimiento semanal de todas las inversiones. */
    private BigDecimal totalInvestmentIncome() {
        return investments.findAll().stream()
                .map(investment -> investment.weeklyReturn())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Suma del aporte semanal de todas las parejas. */
    private BigDecimal totalCoupleContributions() {
        return mortgages.findAll().stream()
                .map(mortgage -> mortgage.weeklyCoupleContribution())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Gasto operativo de la semana. */
    private BigDecimal weeklyOperatingExpenses() {
        return expenses.current().weeklyAmount();
    }

    /** Suma del impuesto y seguro semanal de todas las hipotecas (RN8). */
    private BigDecimal totalEscrowPaid() {
        return mortgages.findAll().stream()
                .map(mortgage -> mortgage.weeklyEscrow())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
