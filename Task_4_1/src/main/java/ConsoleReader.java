import org.jetbrains.annotations.NotNull;

import java.util.Scanner;
import java.util.Stack;

public class ConsoleReader {
    /**
     * считывает строку с консоли
     * @return считанная строка
     */
    @NotNull protected String getLine() {
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(System.in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert sc2 != null;
        if(!sc2.hasNext()) {
            throw new IllegalArgumentException();
        }
        return new Scanner(sc2.nextLine()).nextLine();
    }

    /**
     * @param str строка для разбиения
     * @return токены, полученные из строки разбиением по пробелам
     */
    @NotNull protected Stack<String> getTokens(String str) {
        String[] tokens = str.split(" ");
        Stack<String> tokensStack = new Stack<>();
        for(String token : tokens){
            tokensStack.push(token);
        }
        return tokensStack;
    }
}
