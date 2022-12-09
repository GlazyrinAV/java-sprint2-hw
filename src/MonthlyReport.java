import java.util.ArrayList;
import java.util.HashMap;
import  java.util.Scanner;

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
            for (int i= 0; i<monthlyData.size(); i++) {
                String[] currentData = monthlyData.get(i);
                if (currentData[1].equals("TRUE")) {
                    expens += Double.parseDouble(currentData[2]) * Double.parseDouble(currentData[3]);
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
            for (int i= 0; i<monthlyData.size(); i++) {
                String[] currentData = monthlyData.get(i);
                if (currentData[1].equals("FALSE")) {
                    income += Double.parseDouble(currentData[2]) * Double.parseDouble(currentData[3]);
                }
                monthlyIncome.put(month, income);
            }
        }
        return monthlyIncome;
    }

    /**
     * Метод для нахождения самого прибыльного товара
     */
    void printMonthReport(HashMap<Integer, ArrayList<String[]>> monthlyReport) {
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
        System.out.printf("Максимальная тратта за %2d составила %2f рублей.", month, maxExpense);
    }
}
