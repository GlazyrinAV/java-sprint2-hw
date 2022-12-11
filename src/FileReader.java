import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileReader {

    /**
     * Метод для считывания файла и преобразования данных в Список
     * с массивом строк, которые содержат даннеые из файла.
     * Возвращает Список.
     */
    static ArrayList<String[]> readAndReformFiles (String path) {
        List<String> dataFromFile = readFileContents(path);
        ArrayList<String[]> formattedData = new ArrayList<>();
        if (!dataFromFile.isEmpty()) {
            for (String s : dataFromFile) {
                String[] lineContent = s.split(",");
                formattedData.add(lineContent);
            }
        return formattedData;
        } else {
            return formattedData;
        }
    }

    /**
     * Метод для считывания файла в Лист со строками
     * из файла. Используется в методе readAndReformFiles.
     * Возвращает Лист.
     */
    static List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
}