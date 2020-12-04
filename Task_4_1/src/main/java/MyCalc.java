import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

interface ICommand {
    String getTokenRepresentation();
    void Apply(Stack<Double> calculatorStack);
}

public class MyCalc {

    private final Map<String, ICommand> handlers = new HashMap<>();

    /**
     * добавляет команду в список поддерживаемых текущим экземпляром калькулятора
     * @param handler параметр, полученный из загрузки модуля функцией loadConstructorOfICommand
     */
    public void registerCommand(@NotNull ICommand handler) {
        handlers.put(handler.getTokenRepresentation(),  handler);
    }

    /**
     * основная функция - процессирует стек токенов
     * @return вычисленное выражение
     */
    public double Calculate(@NotNull Stack<String> tokens) {
        Stack<Double> stack = new Stack<>();
        while(!tokens.empty()){
            String token = tokens.pop();
            if(isNumeric(token)){
                stack.push(Double.parseDouble(token));
            }
            else {
                if(handlers.containsKey(token)){
                    handlers.get(token).Apply(stack);
                }
                else {
                    throw new UnsupportedOperationException();
                }
            }
        }
        /* в стеке должно остаться одно число - значение выражения */
        if(stack.size() != 1) {
            throw new IllegalArgumentException();
        }
        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param className имя класса содержащего логику для операции
     * @return новый хендлер для операции, описанной в className
     */
    public ICommand loadConstructorOfICommand(@NotNull String className){
        try {
            ClassLoader classLoader = MyCalc.class.getClassLoader();
            Class aClass = classLoader.loadClass(className);
            Constructor<?> ctor = aClass.getConstructor();
            Object object = ctor.newInstance(new Object[] {});
            return (ICommand) object;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }
}
