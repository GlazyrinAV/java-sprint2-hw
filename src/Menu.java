import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

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

        while (true) {
            printMenu();
            int userInput = getCommand(scanner);

            if (userInput == 1) { // 1. Считать все месячные отчёты
                String fileName;
                ArrayList<String[]> newFile;
                for (int i =1; i <= availableMonth; i++) {
                    fileName = String.format("m.2020%02d.csv", i);
                    newFile = fileReader.readAndReformFiles(fileName);
                    if (!newFile.isEmpty()) {
                    monthlyStatement.put((i), newFile);
                    }
                }
                if (!monthlyStatement.isEmpty()) {
                    isMonthStatmentRead = true;
                    System.out.println("Месячные отчеты списаны.");
                }
            } else if (userInput == 2) { // 2. Считать годовой отчёт
                String fileName;
                ArrayList<String[]> newFile;
                for (int i = firstAvailableYear; i <= (firstAvailableYear + availableYears - 1); i++) {
                    fileName = String.format("y.%d.csv", i);
                    newFile = fileReader.readAndReformFiles(fileName);
                    if (!newFile.isEmpty()) {
                    yearlyStatment.put((i), newFile);
                    }
                }
                if (!yearlyStatment.isEmpty()) {
                    isYearleStatmentRead = true;
                    System.out.println("Годовые отчеты считаны.");
                }
            } else if (userInput == 3) { // 3. Сверить отчёты
                System.out.println("Начата проверка отчетов.");
                if (isMonthStatmentRead && isYearleStatmentRead) {
                    monthlyReport.monthExpenses = monthlyReport.saveMonthExpenses(monthlyStatement);
                    monthlyReport.monthIncome = monthlyReport.saveMonthIncome(monthlyStatement);
                    yearlyReport.yearlyExpenses = yearlyReport.saveYearlyExpenses(yearlyStatment);
                    yearlyReport.yearlyIncome = yearlyReport.saveYearlyIncome(yearlyStatment);
                    boolean isExpensesCheck = false;
                    boolean isIncomeCheck = false;

                    for (int mounth : monthlyReport.monthExpenses.keySet()) {
                        int errors = 0;
                        if (monthlyReport.monthExpenses.get(mounth) != yearlyReport.yearlyExpenses.get(mounth)) {
                            System.out.println("Обнаружена ошибка в расходах за " + (mounth+1) + " месяц");
                            errors++;
                        }
                        if (errors == 0) {
                            isExpensesCheck = true;
                        }
                    }
                    for (int month : monthlyReport.monthIncome.keySet()) {
                        int errors = 0;
                        if (monthlyReport.monthIncome.get(month) != yearlyReport.yearlyIncome.get(month)) {
                            System.out.println("Обнаружена ошибка в доходах за " + (month+1) + " месяц");
                            errors++;
                        }
                        if (errors == 0) {
                            isIncomeCheck = true;
                        }
                    }
                    if (isExpensesCheck && isIncomeCheck) {
                        System.out.println("Проверка отчетов завершена. Ошибок нет.");
                    }
                } else if (!isMonthStatmentRead && !isYearleStatmentRead) {
                    System.out.println("Ежемесячные и годовые отчеты не считаны. Сначала считайте их.");
                } else if (!isYearleStatmentRead) {
                    System.out.println("Годовые отчеты не считаны. Сначала считайте их.");
                } else if (!isMonthStatmentRead ) {
                    System.out.println("Ежемесячные отчеты не считаны. Сначала считайте их.");
                }
            } else if (userInput == 4) { // 4. Вывести информацию о всех месячных отчётах
                monthlyReport.printMonthReport(monthlyStatement);
            } else if (userInput == 5) { // 5. Вывести информацию о годовом отчёте
                yearlyReport.printYearlyReport(yearlyStatment);
            } else if (userInput == 0) { // 0. Выйти из приложения.
                System.out.println("Программа завершена.");
                break;
            } else {
                System.out.println("Такого номера комменады не существует.");
            }
        }
    }

    /**
     * Метод печатает меню
     */

    private static void printMenu() {
        System.out.println(
                "\nВведите, пожалуйста, номер комманды:\n" +
                "1. Считать все месячные отчёты\n" +
                "2. Считать годовой отчёт\n" +
                "3. Сверить отчёты\n" +
                "4. Вывести информацию о всех месячных отчётах\n" +
                "5. Вывести информацию о годовом отчёте\n" +
                "0. Выйти из приложения.");
    }

    /**
     * Метод для получения названия месяца
     * Возвращает название месяца в виде текста
     */

    static String getNameOfMonth(int numberOfMonth) {
        String nameOfMonth = "";
        switch (numberOfMonth) {
            case (1):
                nameOfMonth = "Январь";
                break;
            case (2):
                nameOfMonth = "Февраль";
                break;
            case (3):
                nameOfMonth = "Март";
                break;
            case (4):
                nameOfMonth = "Апрель";
                break;
            case (5):
                nameOfMonth = "Май";
                break;
            case (6):
                nameOfMonth = "Июнь";
                break;
            case (7):
                nameOfMonth = "Июль";
                break;
            case (8):
                nameOfMonth = "Август";
                break;
            case (9):
                nameOfMonth = "Сентябрь";
                break;
            case (10):
                nameOfMonth = "Октябрь";
                break;
            case (11):
                nameOfMonth = "Ноябрь";
                break;
            case (12):
                nameOfMonth = "Декабрь";
                break;
        }
        return nameOfMonth;
    }

    /**
     * Метод для ввода комманды пользователя.
     * Если вводится недопустимый символ (буква, символ и т.п.) - выводится ошибка
     * и запрашивается повторный ввод.
     */
    static int getCommand(Scanner scanner) {
        int userCommand;
        try {
            userCommand = scanner.nextInt();
            return userCommand;
        } catch (InputMismatchException e) {
            System.out.println("Введн недопустимый символ. Введите число.");
            scanner.nextLine();
        }
        return getCommand(scanner);
    }
}