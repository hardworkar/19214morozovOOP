import java.util.Stack;
import org.jetbrains.annotations.NotNull;
public class MyLog implements ICommand{
    @Override
    public String getTokenRepresentation() {
        return "log";
    }
    @Override
    public void Apply(@NotNull Stack<Double> calculatorStack) {
        if(calculatorStack.empty()) {
            throw new IllegalArgumentException("Выражение некорректно");
        }
        calculatorStack.push(Math.log(calculatorStack.pop()));
    }
    public MyLog(){}
}
