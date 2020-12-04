import java.util.Stack;
import org.jetbrains.annotations.NotNull;
public class MySin implements ICommand{
    @Override
    public String getTokenRepresentation() {
        return "sin";
    }
    @Override
    public void Apply(@NotNull Stack<Double> calculatorStack) {
        if(calculatorStack.empty()) {
            throw new IllegalArgumentException("Выражение некорректно");
        }
        calculatorStack.push(Math.sin(calculatorStack.pop()));
    }
    public MySin(){}
}
