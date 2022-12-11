import java.util.ArrayList;
import java.util.HashMap;

public class ReportComparator {
    /**
     * Метод для сверки ежемесячных и годовых отчетов.
     * Сравнение идет, исходя из количества предоставленных месяцев.
     */
    void compareMonthYearRports(HashMap<Integer, ArrayList<String[]>> monthlyStatement, HashMap<Integer, ArrayList<String[]>> yearlyStatement) {
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        monthlyReport.monthExpenses = monthlyReport.saveMonthExpenses(monthlyStatement);
        monthlyReport.monthIncome = monthlyReport.saveMonthIncome(monthlyStatement);
        yearlyReport.yearlyExpenses = yearlyReport.saveYearlyExpenses(yearlyStatement);
        yearlyReport.yearlyIncome = yearlyReport.saveYearlyIncome(yearlyStatement);

        boolean isExpensesCheck = false;
        boolean isIncomeCheck = false;

        for (int month : monthlyReport.monthExpenses.keySet()) {
            int errors = 0;
            if (!monthlyReport.monthExpenses.get(month).equals(yearlyReport.yearlyExpenses.get(month))) {
                System.out.println("Обнаружена ошибка в расходах за " + (month) + " месяц");
                errors++;
            }
            if (errors == 0) {
                isExpensesCheck = true;
            }
        }
        for (int month : monthlyReport.monthIncome.keySet()) {
            int errors = 0;
            if (!monthlyReport.monthIncome.get(month).equals(yearlyReport.yearlyIncome.get(month))) {
                System.out.println("Обнаружена ошибка в доходах за " + (month) + " месяц");
                errors++;
            }
            if (errors == 0) {
                isIncomeCheck = true;
            }
        }
        if (isExpensesCheck && isIncomeCheck) {
            System.out.println("Проверка отчетов завершена. Ошибок нет.");
        }
    }
}