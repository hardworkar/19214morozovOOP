import java.util.Stack;
import org.jetbrains.annotations.NotNull;
public class MySub implements ICommand{
    @Override
    public String getTokenRepresentation() {
        return "-";
    }
    @Override
    public void Apply(@NotNull Stack<Double> calculatorStack) {
        if(calculatorStack.size() < 2) {
            throw new IllegalArgumentException("Выражение некорректно");
        }
        calculatorStack.push(calculatorStack.pop() - calculatorStack.pop());
    }
    public MySub(){}
}
