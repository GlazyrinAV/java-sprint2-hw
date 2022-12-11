import java.util.ArrayList;
import java.util.HashMap;

/**
 * isMonthStatementRead - Проверка на чтение из файла ежемесячных отчетов
 * isYearlyStatementRead - Проверка на чтение из файла годовых отчетов
 * availableMonth - Сколько месяцев доступно для загрузки
 * firstAvailableYear - Первый доступный годовой отчет
 * availableYears - Сколько годов доступно для загрузки, с учетом первого доступного
 * monthlyStatement - сводный первоначальный ежемесячный отчет
 * yearlyStatement - сводный первоначальный ежегодный отчет
 */
public class MainMenu {
    public static void main(String[] args) {
        boolean isMonthStatementRead = false;
        boolean isYearlyStatementRead = false;
        int availableMonth = 3;
        int firstAvailableYear = 2020;
        int availableYears = 1;
        HashMap<Integer, ArrayList<String[]>> monthlyStatement = new HashMap<>();
        HashMap<Integer, ArrayList<String[]>> yearlyStatement= new HashMap<>();
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();
        ReportComparator reportComparator = new ReportComparator(monthlyReport, yearlyReport);

        while (true) {
            printMenu();
            int userInput = SuppurtFunctions.getCommand();

            if (userInput == 1) { // 1. Считать все месячные отчёты
                monthlyStatement = monthlyReport.readMonthReport(availableMonth);
                if (!monthlyStatement.isEmpty()) {
                    isMonthStatementRead = true;
                    System.out.println("Месячные отчеты считаны.");
                } else {
                    System.out.println("При считавании месячных отчетов возникли ошибки.");
                }
            } else if (userInput == 2) { // 2. Считать годовой отчёт
                yearlyStatement = yearlyReport.readYearlyReport(firstAvailableYear, availableYears);
                if (!yearlyStatement.isEmpty()) {
                    isYearlyStatementRead = true;
                    System.out.println("Годовые отчеты считаны.");
                } else {
                    System.out.println("При считавании годовых отчетов возникли ошибки.");
                }
            } else if (userInput == 3) { // 3. Сверить отчёты
                System.out.println("Начата проверка отчетов.");
                if (isMonthStatementRead && isYearlyStatementRead) {
                    reportComparator.compareMonthYearReports(monthlyStatement, yearlyStatement);//
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
}