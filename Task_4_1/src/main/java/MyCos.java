import java.util.Stack;
import org.jetbrains.annotations.NotNull;
public class MyCos implements ICommand{
    @Override
    public String getTokenRepresentation() {
        return "cos";
    }
    @Override
    public void Apply(@NotNull Stack<Double> calculatorStack) {
        if(calculatorStack.empty()) {
            throw new IllegalArgumentException("Выражение некорректно");
        }
        calculatorStack.push(Math.cos(calculatorStack.pop()));
    }
    public MyCos(){}
}
