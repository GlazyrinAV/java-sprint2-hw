import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * isMonthStatmentRead - Проверка на чтение из файла ежемесячных отчетов
 * isYearleStatmentRead - Проверка на чтение из файла годовых отчетов
 * availableMonth - Сколько месяцев доступно для загрузки
 * firstAvailableYear - Первый доступный годовой отчет
 * availableYears - Сколько годов доступно для загрузки, с учетом первого доступного
 * monthlyStatement - сводный первоначальный ежемесячный отчет
 * yearlyStatment - сводный первоначальный ежегодный отчет
 */

public class Menu {
    public static void main(String[] args) {
        int userInput = -1;
        boolean isMonthStatmentRead = false;
        boolean isYearleStatmentRead = false;
        int availableMonth = 3;
        int firstAvailableYear = 2020;
        int availableYears = 1;
        HashMap<Integer, ArrayList<String[]>> monthlyStatement = new HashMap<>();
        HashMap<Integer, ArrayList<String[]>> yearlyStatment= new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        FileReader fileReader = new FileReader();
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        do {
            printMenu();
            userInput = scanner.nextInt();

            if (userInput == 1) { // 1. Считать все месячные отчёты
                String fileName;
                ArrayList<String[]> newFile;
                for (int i =1; i <= availableMonth; i++) {
                    fileName = String.format("m.2020%02d.csv", i);
                    newFile = fileReader.readAndReformFiles(fileName);
                    monthlyStatement.put((i-1), newFile);
                }
                isMonthStatmentRead = true;
            } else if (userInput == 2) { // 2. Считать годовой отчёт
                String fileName;
                ArrayList<String[]> newFile;
                for (int i = firstAvailableYear; i <= (firstAvailableYear + availableYears - 1); i++) {
                    fileName = String.format("y.%d.csv", i);
                    newFile = fileReader.readAndReformFiles(fileName);
                    yearlyStatment.put((i-1), newFile);
                }
                isYearleStatmentRead = true;
            } else if (userInput == 3) { // 3. Сверить отчёты
                if (isMonthStatmentRead && isYearleStatmentRead) {

                    monthlyReport.monthExpenses = monthlyReport.saveMonthExpenses(monthlyStatement);
                    monthlyReport.monthIncome = monthlyReport.saveMonthIncome(monthlyStatement);
                    yearlyReport.yearlyExpenses = yearlyReport.saveYearlyExpenses(yearlyStatment);
                    yearlyReport.yearlyIncome = yearlyReport.saveYearlyIncome(yearlyStatment);
                    boolean isExpensesCheck = false;
                    boolean isIncomeCheck = false;

                    for (int mounth : monthlyReport.monthExpenses.keySet()) {
                        int erorrs = 0;
                        if (monthlyReport.monthExpenses.get(mounth) != yearlyReport.yearlyExpenses.get(mounth)) {
                            System.out.println("Обнаружена ошибка в расходах за " + (mounth+1) + " месяц");
                            erorrs++;
                        }
                        if (erorrs == 0) {
                            isExpensesCheck = true;
                        }
                    }
                    for (int mounth : monthlyReport.monthIncome.keySet()) {
                        int erorrs = 0;
                        if (monthlyReport.monthIncome.get(mounth) != yearlyReport.yearlyIncome.get(mounth)) {
                            System.out.println("Обнаружена ошибка в доходах за " + (mounth+1) + " месяц");
                            erorrs++;
                        }
                        if (erorrs == 0) {
                            isIncomeCheck = true;
                        }
                    }
                    if (isExpensesCheck && isIncomeCheck) {
                        System.out.println("Проверка отчетов завершена. Ошибок нет.");
                    }
                } else if (!isMonthStatmentRead && !isYearleStatmentRead) {
                    System.out.println("Ежемесячные и годовые отчеты не считаны. Сначала считайте их");
                } else if (!isYearleStatmentRead) {
                    System.out.println("Годовые отчеты не считаны. Сначала считайте их");
                } else if (!isMonthStatmentRead) {
                    System.out.println("Ежемесячные отчеты не считаны. Сначала считайте их");
                }
            } else if (userInput == 4) { // 4. Вывести информацию о всех месячных отчётах
                monthlyReport.printMonthReport(monthlyStatement);
            } else if (userInput == 5) { // 5. Вывести информацию о годовом отчёте
                yearlyReport.printYearlyReport(yearlyStatment);
            }
        } while (userInput != 0); // 0. Выйти из приложения.

    }

    private static void printMenu() {
        System.out.println(
                "Введите, пожалуйста, номер комманды:\n" +
                "1. Считать все месячные отчёты\n" +
                "2. Считать годовой отчёт\n" +
                "3. Сверить отчёты\n" +
                "4. Вывести информацию о всех месячных отчётах\n" +
                "5. Вывести информацию о годовом отчёте\n" +
                "0. Выйти из приложения.");
    }
}
