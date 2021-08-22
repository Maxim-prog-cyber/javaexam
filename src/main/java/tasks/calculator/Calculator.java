package tasks.calculator;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public static final String[] OPERATORS = {"(", ")", "+", "-", "/", "*"};
    private Stack<Double> numbers;
    private Stack<String> operations;
    private Map<String, Integer> operationsAndPriority;
    private static boolean isNull = false;
    private int currentOperation = 0;
    private int countOperation = 0;


    public Calculator() {
        this.numbers = new Stack<>();
        this.operations = new Stack<>();
        this.operationsAndPriority = new HashMap<>();
    }

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (!validation(statement)) return null;

        LinkedList<String> strings = new LinkedList<>();
        Pattern p = Pattern.compile("(\\d+(\\.\\d+)?|[)(]|[-+*\\/])");
        Matcher m = p.matcher(statement);
        while (m.find()) {
            strings.add(m.group());
        }

        for (int i = 0; i < strings.size(); i++) {
            if (isOperators(strings.get(i))) {
                countOperation++;
            }
        }

        for (int i = 0; i < strings.size(); i++) {
            String element = strings.get(i);
            try {
                numbers.push(Double.parseDouble(element));

            } catch (NumberFormatException ex) {

                if (element.equals("(")) {
                    operations.push(element);
                    currentOperation++;

                } else if (element.equals(")")) {

                    while (!operations.empty() && !operations.peek().equals("(")) {
                        calculate();
                        if (isNull) return null;
                        operations.pop();
                        currentOperation--;
                        if (countOperation != currentOperation) {
                            break;
                        }

                    }
                } else {
                    if (operations.empty()) {
                        operations.push(element);
                        currentOperation++;

                    } else {
                        int currentPriority = getPriority(element);

                        while (!operations.empty() && !operations.peek().equals("(") && !operations.peek().equals(")") &&
                                currentPriority <= getPriority(operations.peek())) {
                            calculate();
                        }
                        operations.push(element);
                        currentOperation++;
                    }
                }
            }
        }

        while (!operations.empty()) {
            calculate();
        }
        if (operations.empty() && isNull) {
            return null;
        }
        return roundingNumber(numbers.pop());
        // TODO: Implement the logic here
    }


    private void calculate() {
        String operator = operations.pop();
        currentOperation--;
        Double n2 = numbers.pop();
        Double n1 = numbers.pop();
        Double result = null;

        switch (operator) {
            case "+":
                result = n1 + n2;
                break;
            case "-":
                result = n1 - n2;
                break;
            case "*":
                result = n1 * n2;
                break;
            case "/":
                if (n2 == 0) {
                    isNull = true;
                } else {
                    result = n1 / n2;
                }
        }
        numbers.push(result);
    }

    private void putOperationAndPriority() {
        operationsAndPriority.put("-", 1);
        operationsAndPriority.put("+", 1);
        operationsAndPriority.put("*", 2);
        operationsAndPriority.put("/", 2);
    }


    private int getPriority(String c) {
        putOperationAndPriority();
        for (Map.Entry<String, Integer> h : operationsAndPriority.entrySet()) {
            if (c.equals(h.getKey())) {
                return h.getValue();
            }
        }
        return -1;
    }


    private boolean validation(String s) {
        if (s == null || s.equals("")) return false;
        String[] array = s.split("");
        for (int i = 1; i < array.length; i++) {
            if (array[i] == null) return false;

            if (array[i].equals(",") || array[0].equals(",")) {
                System.out.println("You entered:,");
                return false;
            }

        }
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (isOperators(array[i]) && isOperators(array[j]) && array[i].equals(array[j]) || (array[i].equals(".") && array[j].equals("."))) {
                    return false;
                }
            }
        }
        return true;
    }

    private String roundingNumber(double c) {
        DecimalFormat f = new DecimalFormat("####.####");
        String result = f.format(c);
        return result.replaceAll(",", ".");
    }

    private boolean isOperators(String s) {
        for (String s1 : OPERATORS) {
            if (s1.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
