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
     * checkValueToBeExpense - поле, которое получает значение столбца ежемесячного отчета,
     * отвечающего за отнесение статьи к расходам (true) или доходам (false)
     */
    HashMap<Integer, Double> saveYearlyExpenses(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        HashMap<Integer, Double> yearlyExpenses = new HashMap<>();
        if (!yearlyReport.isEmpty()) {
            for (int year : yearlyReport.keySet()) {
                double expense = 0;
                ArrayList<String[]> yearlyData = yearlyReport.get(year);
                for (int i= 1; i<yearlyData.size(); i++) {
                    String checkValueToBeExpense = yearlyData.get(i)[2];
                    if (checkValueToBeExpense.equals("true")) {
                        expense = Double.parseDouble(yearlyData.get(i)[1]);
                    }
                    yearlyExpenses.put(Integer.parseInt(yearlyData.get(i)[0]), expense);
                }
            }
        } else {
            System.out.println("Ежегодный отчет пуст.");
        }
        return yearlyExpenses;
    }

    /**
     * Метод для сохранения всех доходов помесячно
     * checkValueToBeIncome - поле, которое получает значение столбца ежемесячного отчета,
     * отвечающего за отнесение статьи к расходам (true) или доходам (false)
     */
    HashMap<Integer, Double> saveYearlyIncome(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        HashMap<Integer, Double> yearlyIncome = new HashMap<>();
        if (!yearlyReport.isEmpty()) {
            for (int year : yearlyReport.keySet()) {
                double income = 0;
                ArrayList<String[]> yearlyData = yearlyReport.get(year);
                for (int i= 1; i<yearlyData.size(); i++) {
                    String checkValueToBeIncome = yearlyData.get(i)[2];
                    if (checkValueToBeIncome.equals("false")) {
                        income = Double.parseDouble(yearlyData.get(i)[1]);
                    }
                    yearlyIncome.put(Integer.parseInt(yearlyData.get(i)[0]), income);
                }
            }
        } else {
            System.out.println("Ежегодный отчет пуст.");
        }
        return yearlyIncome;
    }

    /**
     * Метод для печати годового отчета
     */
    void printYearlyReport(HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        if (!yearlyReport.isEmpty()) {
            for (int year : yearlyReport.keySet()) {
                System.out.println(year);
                for (int i = 1; i < getAvailableMonths(yearlyReport); i++) {
                System.out.println("Чистая прибыль за " + SupportFunctions.getNameOfMonth(i) + " составила: "
                        + getNetProfitByMonth(yearlyReport, i) + " рублей.");
                }
                System.out.printf("\nСредние доходы составили: %.1f рублей.", getAverage(saveYearlyIncome(yearlyReport)));
                System.out.printf("\nСредние расходы составили: %.1f рублей.\n", getAverage(saveYearlyExpenses(yearlyReport)));
            }
        } else {
            System.out.println("Ежегодный отчет пуст.");
        }
    }

    /**
     * Метод для сохранения прибыли. Возвращает разницу между доходами и расходами
     * income - поле, получающее значение доходов за месяц
     * expense - поле, получающее значение расходов за месяц
     */
    double getNetProfitByMonth(HashMap<Integer, ArrayList<String[]>> yearlyReport, int month) {
        double netProfit = 0;
        if (!yearlyReport.isEmpty()) {
            HashMap<Integer, Double> expenseData = saveYearlyExpenses(yearlyReport);
            HashMap<Integer, Double> incomeData = saveYearlyIncome(yearlyReport);
            double income = incomeData.get(month);
            double expense = expenseData.get(month);
            netProfit = income - expense;
        } else {
            System.out.println("Ежегодный отчет пуст.");
        }
        return netProfit;
    }

    /**
     * Метод для поиска средних значений
     * Возвращает среднее значение
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

    /**
     * Метод для поиска допустимого количества месяцев (т.е. наличие строк как расходов, так и доходов)
     * в ежегодном отчете. Возвращает значение количества месяцев.
     */
    int getAvailableMonths (HashMap<Integer, ArrayList<String[]>> yearlyReport) {
        HashMap<Integer, Double> expenseData = saveYearlyExpenses(yearlyReport);
        HashMap<Integer, Double> incomeData = saveYearlyIncome(yearlyReport);
        int availableMonths = Integer.min(expenseData.size(), incomeData.size());
        return availableMonths;
    }
}