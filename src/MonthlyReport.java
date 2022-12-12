import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    HashMap<Integer, Double> monthExpenses = new HashMap<>();
    HashMap<Integer, Double> monthIncome = new HashMap<>();

    /**
     *  Метод для чтения ежемесячных отчетов
     *  Возвращает хэш таблицу с данными ежемесячных отчетов в String
     */
    HashMap<Integer, ArrayList<String[]>> readMonthReport(int availableMonth) {
        HashMap<Integer, ArrayList<String[]>> monthlyStatement = new HashMap<>();
        String fileName;
        for (int i =1; i <= availableMonth; i++) {
            fileName = String.format("m.2020%02d.csv", i);
            ArrayList<String[]> newFile = FileReader.readAndReformFiles(fileName);
            if (!newFile.isEmpty()) {
            monthlyStatement.put((i), newFile);
            }
        }
        return monthlyStatement;
    }

    /**
     * Метод для сохранения всех расходов помесячно
     * checkValueToBeExpense - поле, которое получает значение столбца ежемесячного отчета,
     * отвечающего за отнесение статьи к расходам (TRUE) или доходам (FALSE)
     */
    HashMap<Integer, Double> saveMonthExpenses(HashMap<Integer, ArrayList<String[]>> monthlyReport) {
        HashMap<Integer, Double> monthlyExpenses = new HashMap<>();
        if (!monthlyReport.isEmpty()) {
            for (int month : monthlyReport.keySet()) {
                double expense = 0;
                ArrayList<String[]> monthlyData = monthlyReport.get(month);
                for (int i= 1; i<monthlyData.size(); i++) {
                    String checkValueToBeExpense = monthlyData.get(i)[1];
                    if (checkValueToBeExpense.equals("TRUE")) {
                        expense += Double.parseDouble(monthlyData.get(i)[2]) * Double.parseDouble(monthlyData.get(i)[3]);
                    }
                    monthlyExpenses.put(month, expense);
                }
            }
        } else {
            System.out.println("Помесячный отчет пуст.");
        }
        return monthlyExpenses;
    }

    /**
     * Метод для сохранения всех доходов помесячно
     * checkValueToBeIncome - поле, которое получает значение столбца ежемесячного отчета,
     * отвечающего за отнесение статьи к расходам (TRUE) или доходам (FALSE)
     */
    HashMap<Integer, Double> saveMonthIncome(HashMap<Integer, ArrayList<String[]>> monthlyReport) {
        HashMap<Integer, Double> monthlyIncome = new HashMap<>();
        if (!monthlyReport.isEmpty()) {
            for (int month : monthlyReport.keySet()) {
                double income = 0;
                ArrayList<String[]> monthlyData = monthlyReport.get(month);
                for (int i= 1; i<monthlyData.size(); i++) {
                    String checkValueToBeIncome = monthlyData.get(i)[1];
                    if (checkValueToBeIncome.equals("FALSE")) {
                        income += Double.parseDouble(monthlyData.get(i)[2]) * Double.parseDouble(monthlyData.get(i)[3]);
                    }
                    monthlyIncome.put(month, income);
                }
            }
        } else {
            System.out.println("Помесячный отчет пуст.");
        }
        return monthlyIncome;
    }

    /**
     * Метод для вывода отчета помесячно
     */
    void printMonthReport(HashMap<Integer, ArrayList<String[]>> monthlyReport) {
        if (!monthlyReport.isEmpty()) {
            for (int month : monthlyReport.keySet()) {
                String[] mostProfitable = getMostProfitable(monthlyReport, month);
                System.out.println("Месяц " + SupportFunctions.getNameOfMonth(month));
                System.out.println(
                        "Самый прибыльный товар: " + mostProfitable[0] +
                        "\nПрибыль составила: " + mostProfitable[1] + " рублей.");
                System.out.printf("Максимальная трата за " + SupportFunctions.getNameOfMonth(month) +
                        " составила %.1f рублей.", getMaxExpenseReport(monthlyReport, month));
                System.out.println("");
        }
        } else {
            System.out.println("Помесячный отчет пуст.");
        }
    }

    /**
     * Метод для нахождения самого прибыльного товара. Возвращает строчный массив, в котором
     * первое значение содержит название самой прибыльной позиции, а второе - его величину.
     * checkValueToBeIncome - поле, которое получает значение столбца ежемесячного отчета,
     * отвечающего за отнесение статьи к расходам (TRUE) или доходам (FALSE)
     */
    String[] getMostProfitable(HashMap<Integer, ArrayList<String[]>> monthlyReport, int month) {
        String nameOfMostProfitable = "";
        String valueOfMostProfitable = "0";
        if (!monthlyReport.isEmpty()) {
        ArrayList<String[]> monthlyData = monthlyReport.get(month);
        for (int i= 0; i<monthlyData.size(); i++) {
            String[] currentData = monthlyData.get(i);
            String checkValueToBeIncome = currentData[1];
            if (checkValueToBeIncome.equals("FALSE")) {
                Double expense = Double.parseDouble(currentData[2]) * Double.parseDouble(currentData[3]);
                if (Double.parseDouble(valueOfMostProfitable) < expense) {
                    nameOfMostProfitable = currentData[0];
                    valueOfMostProfitable = expense.toString();
                }
            }
        }
        String[] answer = {nameOfMostProfitable, valueOfMostProfitable};
        return answer;
        } else {
            System.out.println("Помесячный отчет пуст.");
            String[] answer = {"", ""};
            return answer;
        }
    }

    /**
     * Метод для поиска самой большой траты. Возвращает значение максимальной траты в выбранном месяце.
     * checkValueToBeExpense - поле, которое получает значение столбца ежемесячного отчета,
     * отвечающего за отнесение статьи к расходам (TRUE) или доходам (FALSE)
     */
    double getMaxExpenseReport(HashMap<Integer, ArrayList<String[]>> monthlyReport, Integer month) {
        double maxExpense = 0;
        if (!monthlyReport.isEmpty()) {
            ArrayList<String[]> monthlyData = monthlyReport.get(month);
            for (int i= 0; i<monthlyData.size(); i++) {
                String[] currentData = monthlyData.get(i);
                String checkValueToBeExpense = currentData[1];
                if (checkValueToBeExpense.equals("TRUE")) {
                    double expense = Double.parseDouble(currentData[2]) * Double.parseDouble(currentData[3]);
                    if (maxExpense < expense) {
                        maxExpense = expense;
                    }
                }
            }
        } else {
            System.out.println("Помесячный отчет пуст.");
        }
        return maxExpense;
    }
}