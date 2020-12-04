import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class MyCalcTest {

    @Test(expected = UnsupportedOperationException.class)
    public void unsupported_op() {
        MyCalc calc = new MyCalc();
        calc.registerCommand(calc.loadConstructorOfICommand("MySin"));
        Stack<String> tokens = new Stack<>();
        String [] toks = new String[]{"cos", "1"};
        for(String token : toks){
            tokens.push(token);
        }
        calc.Calculate(tokens);
    }

    @Test
    public void calculate1() {
        MyCalc calc = new MyCalc();
        calc.registerCommand(calc.loadConstructorOfICommand("MySin"));
        calc.registerCommand(calc.loadConstructorOfICommand("MySub"));
        calc.registerCommand(calc.loadConstructorOfICommand("MyPow"));
        Stack<String> tokens = new Stack<>();
        String [] toks = new String[]{"sin", "-", "8", "pow", "2", "3"};
        for(String token : toks){
            tokens.push(token);
        }
        assertEquals(calc.Calculate(tokens), 0.0, 0.001);
    }

    @Test
    public void calculate2() {
        MyCalc calc = new MyCalc();
        calc.registerCommand(calc.loadConstructorOfICommand("MySqrt"));
        calc.registerCommand(calc.loadConstructorOfICommand("MySub"));
        Stack<String> tokens = new Stack<>();
        String [] toks = new String[]{"sqrt", "-", "8", "6"};
        for(String token : toks){
            tokens.push(token);
        }
        assertEquals(calc.Calculate(tokens), 1.414, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrong_expression() {
        MyCalc calc = new MyCalc();
        calc.registerCommand(calc.loadConstructorOfICommand("MySin"));
        calc.registerCommand(calc.loadConstructorOfICommand("MySub"));
        calc.registerCommand(calc.loadConstructorOfICommand("MyPow"));
        Stack<String> tokens = new Stack<>();
        String [] toks = new String[]{"sin"};
        for(String token : toks){
            tokens.push(token);
        }
        calc.Calculate(tokens);
    }

    @Test
    public void calculate_nan() {
        MyCalc calc = new MyCalc();
        calc.registerCommand(calc.loadConstructorOfICommand("MyLog"));
        Stack<String> tokens = new Stack<>();
        String [] toks = new String[]{"log", "-1"};
        for(String token : toks){
            tokens.push(token);
        }
        assertTrue(Double.isNaN(calc.Calculate(tokens)));
    }
}