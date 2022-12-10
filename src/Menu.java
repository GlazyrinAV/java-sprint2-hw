import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * isMonthStatementRead - Проверка на чтение из файла ежемесячных отчетов
 * isYearlyStatementRead - Проверка на чтение из файла годовых отчетов
 * availableMonth - Сколько месяцев доступно для загрузки
 * firstAvailableYear - Первый доступный годовой отчет
 * availableYears - Сколько годов доступно для загрузки, с учетом первого доступного
 * monthlyStatement - сводный первоначальный ежемесячный отчет
 * yearlyStatement - сводный первоначальный ежегодный отчет
 */
public class Menu {
    public static void main(String[] args) {
        boolean isMonthStatementRead = false;
        boolean isYearlyStatementRead = false;
        int availableMonth = 3;
        int firstAvailableYear = 2020;
        int availableYears = 1;
        HashMap<Integer, ArrayList<String[]>> monthlyStatement = new HashMap<>();
        HashMap<Integer, ArrayList<String[]>> yearlyStatement= new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        FileReader fileReader = new FileReader();
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        ReportComparator reportComparator = new ReportComparator();

        while (true) {
            printMenu();
            int userInput = getCommand(scanner);

            if (userInput == 1) { // 1. Считать все месячные отчёты
                String fileName;
                for (int i =1; i <= availableMonth; i++) {
                    fileName = String.format("m.2020%02d.csv", i);
                    ArrayList<String[]> newFile = fileReader.readAndReformFiles(fileName);
                    if (!newFile.isEmpty()) {
                        monthlyStatement.put((i), newFile);
                    }
                }
                if (!monthlyStatement.isEmpty()) {
                    isMonthStatementRead = true;
                    System.out.println("Месячные отчеты считаны.");
                }
            } else if (userInput == 2) { // 2. Считать годовой отчёт
                String fileName;
                for (int i = firstAvailableYear; i <= (firstAvailableYear + availableYears - 1); i++) {
                    fileName = String.format("y.%d.csv", i);
                    ArrayList<String[]> newFile = fileReader.readAndReformFiles(fileName);
                    if (!newFile.isEmpty()) {
                        yearlyStatement.put((i), newFile);
                    }
                }
                if (!yearlyStatement.isEmpty()) {
                    isYearlyStatementRead = true;
                    System.out.println("Годовые отчеты считаны.");
                }
            } else if (userInput == 3) { // 3. Сверить отчёты
                System.out.println("Начата проверка отчетов.");
                if (isMonthStatementRead && isYearlyStatementRead) {
                    reportComparator.compareMonthYearRports(monthlyStatement, yearlyStatement);//
                } else if (!isMonthStatementRead && !isYearlyStatementRead) {
                    System.out.println("Ежемесячные и годовые отчеты не считаны. Сначала считайте их.");
                } else if (!isYearlyStatementRead) {
                    System.out.println("Годовые отчеты не считаны. Сначала считайте их.");
                } else if (!isMonthStatementRead ) {
                    System.out.println("Ежемесячные отчеты не считаны. Сначала считайте их.");
                }
            } else if (userInput == 4) { // 4. Вывести информацию о всех месячных отчётах
                monthlyReport.printMonthReport(monthlyStatement);
            } else if (userInput == 5) { // 5. Вывести информацию о годовом отчёте
                yearlyReport.printYearlyReport(yearlyStatement);
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