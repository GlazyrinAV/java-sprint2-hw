import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    HashMap<Integer, Double> yearlyExpenses = new HashMap<>();
    HashMap<Integer, Double> yearlyIncome = new HashMap<>();


    /**
     * Метод для сохранения всех расходов помесячно
     */
    HashMap<Integer, Double> saveYearlyExpenses(HashMap<Integer, ArrayList<String[]>> yearlyReport) {

        HashMap<Integer, Double> yearlyExpenses = new HashMap<>();
        ArrayList<String[]> yearlyData;
        String[] currentData;
        for (int month : yearlyReport.keySet()) {
            double expens = 0;
            yearlyData = yearlyReport.get(month);
            for (int i= 0; i<yearlyData.size(); i++) {
                currentData = yearlyData.get(i);
                if (currentData[2].equals("TRUE")) {
                    expens = Double.parseDouble(currentData[1]);
                }
                yearlyExpenses.put(month, expens);
            }
        }
        return yearlyExpenses;
    }


    /**
     * Метод для сохранения всех доходов помесячно
     */
    HashMap<Integer, Double> saveYearlyIncome(HashMap<Integer, ArrayList<String[]>> yearlyReport) {

        HashMap<Integer, Double> yearlyExpenses = new HashMap<>();
        ArrayList<String[]> yearlyData;
        String[] currentData;
        for (int month : yearlyReport.keySet()) {
            double income = 0;
            yearlyData = yearlyReport.get(month);
            for (int i= 0; i<yearlyData.size(); i++) {
                currentData = yearlyData.get(i);
                if (currentData[2].equals("FALSE")) {
                    income = Double.parseDouble(currentData[1]);
                }
                yearlyExpenses.put(month, income);
            }
        }
        return yearlyExpenses;
    }


    /**
     * Метод для сохранения прибыли
     */



    /**
     * Метод для поиска средних доходов или расходов
     */
    void printYearlyReport(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
    }

}
