import java.util.Stack;
import org.jetbrains.annotations.NotNull;
public class MySqrt implements ICommand{
    @Override
    public String getTokenRepresentation() {
        return "sqrt";
    }
    @Override
    public void Apply(@NotNull Stack<Double> calculatorStack) {
        if(calculatorStack.empty()) {
            throw new IllegalArgumentException("Выражение некорректно");
        }
        calculatorStack.push(Math.sqrt(calculatorStack.pop()));
    }
    public MySqrt(){}
}
