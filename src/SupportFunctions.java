import java.util.InputMismatchException;
import java.util.Scanner;

public class SupportFunctions {
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
    static int getCommand() {
        Scanner scanner = new Scanner(System.in);
        String userCommand;
        try {
            userCommand = scanner.nextLine();
            userCommand = userCommand.trim();
            int command = Integer.parseInt(userCommand);
            return command;
        } catch (NumberFormatException e) {
            System.out.println("Введен недопустимый символ. Введите число.");
        }
        return getCommand();
    }
}