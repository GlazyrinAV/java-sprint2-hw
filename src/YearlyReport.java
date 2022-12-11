import java.util.ArrayList;
import java.util.HashMap;

public class YearlyReport {
    HashMap<Integer, Double> yearlyExpenses = new HashMap<>();
    HashMap<Integer, Double> yearlyIncome = new HashMap<>();

    /**
     *  Метод для чтения ежегодных отчетов
     *  Возвращает хэш таблицу с данными ежемесячных отчетов в String
     */
    HashMap<Integer, ArrayList<String[]>> readYearlyReport(int firstAvailableYear, int availableYears) {
        HashMap<Integer, ArrayList<String[]>> yearlyStatement= new HashMap<>();
        String fileName;
        for (int i = firstAvailableYear; i <= (firstAvailableYear + availableYears - 1); i++) {
            fileName = String.format("y.%d.csv", i);
            ArrayList<String[]> newFile = FileReader.readAndReformFiles(fileName);
            if (!newFile.isEmpty()) {
                yearlyStatement.put((i), newFile);
            }
        }
        return  yearlyStatement;
    }

    /**
     * Метод для сохранения всех расходов помесячно
     */
    HashMap<Integer, Double> saveYearlyExpenses(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        HashMap<Integer, Double> yearlyExpenses = new HashMap<>();
        for (int year : yearlyReport.keySet()) {
            double expense = 0;
            ArrayList<String[]> yearlyData = yearlyReport.get(year);
            for (int i= 1; i<yearlyData.size(); i++) {
                if ((yearlyData.get(i))[2].equals("true")) {
                    expense = Double.parseDouble(yearlyData.get(i)[1]);
                }
                yearlyExpenses.put(Integer.parseInt(yearlyData.get(i)[0]), expense);
            }
        }
        return yearlyExpenses;
    }

    /**
     * Метод для сохранения всех доходов помесячно
     */
    HashMap<Integer, Double> saveYearlyIncome(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        HashMap<Integer, Double> yearlyIncome = new HashMap<>();
        for (int year : yearlyReport.keySet()) {
            double income = 0;
            ArrayList<String[]> yearlyData = yearlyReport.get(year);
            for (int i= 1; i<yearlyData.size(); i++) {
                if (yearlyData.get(i)[2].equals("false")) {
                    income = Double.parseDouble(yearlyData.get(i)[1]);
                }
                yearlyIncome.put(Integer.parseInt(yearlyData.get(i)[0]), income);
            }
        }
        return yearlyIncome;
    }

    /**
     * Метод для печати годового отчета
     */
    void printYearlyReport(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        for (int year : yearlyReport.keySet()) {
            System.out.println(year);
            printNetProfitByMonth(yearlyReport);
            System.out.printf("\nСредние доходы составили: %.1f рублей.", getAverage(saveYearlyIncome(yearlyReport)));
            System.out.printf("\nСредние расходы составили: %.1f рублей.\n", getAverage(saveYearlyExpenses(yearlyReport)));
        }
    }

    /**
     * Метод для сохранения прибыли
     */
    void printNetProfitByMonth(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        double netProfit = 0;
        HashMap<Integer, Double> expenseData = saveYearlyExpenses(yearlyReport);
        HashMap<Integer, Double> incomeData = saveYearlyIncome(yearlyReport);
        for (int i = 1; i <= expenseData.size(); i++) {
            netProfit = incomeData.get(i) - expenseData.get(i);
            System.out.println("Чистая прибыль за " + SuppurtFunctions.getNameOfMonth(i) + " составила: " + netProfit + " рублей.");
        }
    }

    /**
     * Метод для поиска средних доходов или расходов
     */
    double getAverage(HashMap<Integer, Double> data) {
        double average;
        double sum = 0;
        for (int i =1; i <= data.size(); i++) {
            sum += data.get(i);
        }
        average = sum / (data.size()-1);
        return average;
    }
}