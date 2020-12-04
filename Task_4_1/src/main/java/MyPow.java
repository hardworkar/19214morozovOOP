import java.util.Stack;
import org.jetbrains.annotations.NotNull;
public class MyPow implements ICommand{
    @Override
    public String getTokenRepresentation() {
        return "pow";
    }
    @Override
    public void Apply(@NotNull Stack<Double> calculatorStack) {
        if(calculatorStack.size() < 2) {
            throw new IllegalArgumentException("Выражение некорректно");
        }
        calculatorStack.push(Math.pow(calculatorStack.pop(), calculatorStack.pop()));
    }
    public MyPow(){}
}
