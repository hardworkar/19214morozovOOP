import java.util.Stack;

/* ммм, консольное приложение */
public class Task_4_1 {
    public static void main(String[] args) {
        MyCalc calc = new MyCalc();
        calc.registerCommand(calc.loadConstructorOfICommand("MyAdd"));
        calc.registerCommand(calc.loadConstructorOfICommand("MySub"));
        calc.registerCommand(calc.loadConstructorOfICommand("MyTimes"));
        calc.registerCommand(calc.loadConstructorOfICommand("MyDiv"));
        calc.registerCommand(calc.loadConstructorOfICommand("MyLog"));
        calc.registerCommand(calc.loadConstructorOfICommand("MyPow"));
        calc.registerCommand(calc.loadConstructorOfICommand("MySqrt"));
        calc.registerCommand(calc.loadConstructorOfICommand("MySin"));
        calc.registerCommand(calc.loadConstructorOfICommand("MyCos"));

        ConsoleReader myConsole = new ConsoleReader();
        Stack<String> tokens = myConsole.getTokens(myConsole.getLine());

        System.out.println(calc.Calculate(tokens));
    }

}
