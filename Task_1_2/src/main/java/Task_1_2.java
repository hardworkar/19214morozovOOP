import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Task_1_2 {
    public static void main(String[] args) throws IOException {
        // ввод с консоли
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.print("Введите имя файла: ");
        String inputFileName = consoleReader.readLine();
        System.out.print("Введите подстроку: ");
        String subString = consoleReader.readLine();

        // открытие файла
        BufferedReader fileReader = Files.newBufferedReader(Path.of(inputFileName), StandardCharsets.UTF_8);

        /* Чтение файла
        * Чтение производится чанками по chunkSize символов. Предполагается, что длина подстроки << chunkSize.
        * Поиск подстроки производится отдельно в каждом чанке, а так же в конкатенации двух соседних чанков.
        * Второе делается в предположении, что подстрока может лежать на пересечении чанков.
        * */
        int bytesRead;
        int chunksNum = 0;
        String s;
        long charsRead = 0;
        int chunkSize = 1024;
        char[] buffer_1 = new char[chunkSize], buffer_2 = new char[0];
        long rightChunkIdx = 0;
        ArrayList<Long> res = new ArrayList<>();
        while((bytesRead = fileReader.read(buffer_1,0,chunkSize)) != -1){
            chunksNum++;
            s = String.valueOf(buffer_2) + String.valueOf(buffer_1);
            for(int i = s.indexOf(subString) ; i != -1 && i < s.length() ; i = s.indexOf(subString, i+1)){
                long entry = i + (rightChunkIdx - 1 > 0 ? (rightChunkIdx - 1) : 0) * chunkSize;
                if(res.size() == 0 || res.get(res.size() - 1) != entry)
                    res.add(entry);
            }
            if(buffer_2.length == 0)
                buffer_2 = new char[chunkSize];
            System.arraycopy(buffer_1, 0, buffer_2, 0, buffer_1.length);
            charsRead += bytesRead;
            rightChunkIdx++;
        }
        System.out.println("Chunks: " + chunksNum + "\nChars: " + charsRead);
        System.out.print("Вхождения: " + res.toString());
        fileReader.close();
    }
}
