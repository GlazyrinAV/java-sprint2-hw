import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    HashMap<Integer, Double> monthExpenses = new HashMap<>();
    HashMap<Integer, Double> monthIncome = new HashMap<>();

    /**
     * Метод для сохранения всех расходов помесячно
     */
    HashMap<Integer, Double> saveMonthExpenses(HashMap<Integer, ArrayList<String[]>> monthlyReport) {

        HashMap<Integer, Double> monthlyExpenses = new HashMap<>();
        for (int month : monthlyReport.keySet()) {
            double expens = 0;
            ArrayList<String[]> monthlyData = monthlyReport.get(month);
            for (int i= 1; i<monthlyData.size(); i++) {
                if (monthlyData.get(i)[1].equals("TRUE")) {
                    expens += Double.parseDouble(monthlyData.get(i)[2]) * Double.parseDouble(monthlyData.get(i)[3]);
                }
                monthlyExpenses.put(month, expens);
            }
        }
        return monthlyExpenses;
    }


    /**
     * Метод для сохранения всех доходов помесячно
     */
    HashMap<Integer, Double> saveMonthIncome(HashMap<Integer, ArrayList<String[]>> monthlyReport) {

        HashMap<Integer, Double> monthlyIncome = new HashMap<>();
        for (int month : monthlyReport.keySet()) {
            double income = 0;
            ArrayList<String[]> monthlyData = monthlyReport.get(month);
            for (int i= 1; i<monthlyData.size(); i++) {
                if (monthlyData.get(i)[1].equals("FALSE")) {
                    income += Double.parseDouble(monthlyData.get(i)[2]) * Double.parseDouble(monthlyData.get(i)[3]);
                }
                monthlyIncome.put(month, income);
            }
        }
        return monthlyIncome;
    }

    /**
     * Метод для вывода отчета помесячно
     */

    void printMonthReport(HashMap<Integer, ArrayList<String[]>> monthlyReport) {
        for (int month : monthlyReport.keySet()) {
            String[] mostProfitible = findMostProfitible(monthlyReport, month);
            System.out.println("Месяц " + Menu.getNameOfMonth(month));
            System.out.println(
                    "Самый прибыльный товар: " + mostProfitible[0] +
                    "\nПрибыль составила: " + mostProfitible[1] + " рублей.");
            getMaxExpenseReport(monthlyReport, month);
            System.out.println("");
        }
    }

    /**
     * Метод для нахождения самого прибыльного товара
     */
    String[] findMostProfitible(HashMap<Integer, ArrayList<String[]>> monthlyReport, int month) {
        String nameOfMostProfitible = "";
        String valueOfMostProfitible = "0";
        ArrayList<String[]> monthlyData = monthlyReport.get(month);
        for (int i= 0; i<monthlyData.size(); i++) {
            String[] currentData = monthlyData.get(i);
            if (currentData[1].equals("FALSE")) {
                Double expense = Double.parseDouble(currentData[2]) * Double.parseDouble(currentData[3]);
                if (Double.parseDouble(valueOfMostProfitible) < expense) {
                    nameOfMostProfitible = currentData[0];
                    valueOfMostProfitible = expense.toString();
                }
            }
        }
        String[] answer = {nameOfMostProfitible, valueOfMostProfitible};
        return answer;
    }

    /**
     * Метод для поиска самой большой траты
     */
    void getMaxExpenseReport(HashMap<Integer, ArrayList<String[]>> monthlyReport, Integer month) {
        double maxExpense = 0;
        ArrayList<String[]> monthlyData = monthlyReport.get(month);
        for (int i= 0; i<monthlyData.size(); i++) {
            String[] currentData = monthlyData.get(i);
            if (currentData[1].equals("TRUE")) {
                double expense = Double.parseDouble(currentData[2]) * Double.parseDouble(currentData[3]);
                if (maxExpense < expense) {
                    maxExpense = expense;
                }
            }
        }
        System.out.printf("Максимальная тратта за" + Menu.getNameOfMonth(month) +  "составила %.1f рублей.", maxExpense);
    }
}