import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    
    // Método para evaluar la expresión
    public static double evaluate(String expression) {
        // Convierte la expresión infija a notación posfija (postfix)
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    // Método para convertir de notación infija a posfija
    private static String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Si es un número o un punto decimal
            if (Character.isDigit(c) || c == '.') {
                result.append(c);
            }
            // Si es un operador
            else if (isOperator(c)) {
                result.append(' ');
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
            // Si es un paréntesis de apertura
            else if (c == '(') {
                stack.push(c);
            }
            // Si es un paréntesis de cierre
            else if (c == ')') {
                result.append(' ');
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop()).append(' ');
                }
                stack.pop();  // Elimina el '('
            }
        }

        // Vaciar la pila de los operadores restantes
        while (!stack.isEmpty()) {
            result.append(' ').append(stack.pop());
        }

        return result.toString();
    }

    // Método para evaluar la expresión en notación posfija
    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        Scanner tokens = new Scanner(postfix);

        while (tokens.hasNext()) {
            if (tokens.hasNextDouble()) {
                stack.push(tokens.nextDouble());
            } else {
                String token = tokens.next();
                double b = stack.pop();
                double a = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                    case "^":
                        stack.push(Math.pow(a, b));
                        break;
                }
            }
        }

        return stack.pop();
    }

    // Método para determinar la precedencia de los operadores
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    // Método para verificar si el carácter es un operador
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce una expresión aritmética:");
        String expression = scanner.nextLine();
        
        try {
            double result = evaluate(expression);
            System.out.println("Resultado: " + result);
        } catch (Exception e) {
            System.out.println("Error en la evaluación de la expresión.");
        }

        scanner.close();
    }
}
