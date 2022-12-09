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
    ArrayList<String[]> readAndReformFiles (String path) {
        List<String> dataFromFile = readFileContents(path);
        ArrayList<String[]> formatedData = new ArrayList<>();
        for (int i = 1; i < dataFromFile.size(); i++) {
            String line = dataFromFile.get(i);
            String[] lineContents = line.split(",");
            formatedData.add(lineContents);
        }
        return formatedData;
    }

    /**
     * Метод для считывания файла в Лист со строками
     * из файла. Используется в методе readAndReformFiles.
     * Возвращает Лист.
     */
    List<String> readFileContents(String path) {
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
}
