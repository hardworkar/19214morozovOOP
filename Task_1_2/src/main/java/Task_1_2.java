import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Task_1_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.print("Введите имя файла: ");
        String inputFileName = consoleReader.readLine();
        System.out.print("Введите подстроку: ");
        String subString = consoleReader.readLine();


        File file = new File(inputFileName);
        if(!file.exists())
            System.out.println("Такого файла не существует");
        BufferedReader fileReader = Files.newBufferedReader(Path.of(inputFileName), StandardCharsets.UTF_8);
        ArrayList<Long> result = new ArrayList<>();
        char[] buffer_1 = new char[100], buffer_2 = new char[100];
        int bytesRead = 0;
        int chunksNum = 0;
        String s;
        long charsRead = 0;
        while((bytesRead = fileReader.read(buffer_1,0,100)) != -1){
            chunksNum ++;
            s = String.valueOf(buffer_1);
            for(int i = s.indexOf(subString) ; i != -1 && i < s.length() ; i = s.indexOf(subString, i+1)){
                result.add(i + charsRead);
            }
            if(chunksNum == 17578126)
                System.out.println("\"" + s + "\", len = " + s.length());
            charsRead += bytesRead;
        }
        System.out.println("Chunks: " + chunksNum + "\nChars: " + charsRead);
        System.out.print("Вхождения: " + result.toString());
        fileReader.close();


    }
}
