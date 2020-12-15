import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandParser {
    protected ArrayList<String> parseLine(@NotNull String enteredString){
        /* выглядит очень страшно, я просто скопировал регулярку со стак оверфлоу и искренне верю что она работает, у ответа было много галочек
         *  она парсит выражения в кавычках, учитывая экранированные кавычки внутри строк */
        String rx = "[^\"\\s]+|\"(\\\\.|[^\\\\\"])*\"";
        ArrayList<String> cmdline = new ArrayList<>();
        Scanner lineScanner = new Scanner(enteredString);
        /* забиваем cmdline токенами пока можем */
        while(true){
            String token = lineScanner.findInLine(rx);
            if(token == null){
                break;
            }
            cmdline.add(token);
        }
        return cmdline;
    }
}
