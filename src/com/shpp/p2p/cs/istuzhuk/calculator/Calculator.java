package com.shpp.p2p.cs.istuzhuk.calculator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is main class in the "Calculator" project
 */
public class Calculator {
    /**
     * The main method takes arguments from user and parses them to calculate an expression
     *
     * @param args entered data by user
     */
    public static void main(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        if (args.length >= 1) {
            String formula = args[0].replaceAll(" ", "").replaceAll(",", ".");
            if (Check.parenthesesAreCorrect(formula)) {
                variables = Parsing.parseArguments(variables, args);
                calculate(formula, variables);
            } else
                System.out.println("Error in the parentheses");
        } else
            System.out.println("Enter expression");
    }

    /**
     * This method takes entered formula and parsed variables.
     * It's this method that calls the corresponding methods for operators
     *
     * @param formula   entered expression by user
     * @param variables entered variables by user
     */
    private static void calculate(String formula, HashMap<String, Double> variables) {
        ArrayList<String> parsedFormula = new ArrayList<>();
        double result = 0;
        parsedFormula = Parsing.parseFormula(parsedFormula, formula);
        parsedFormula = Parsing.substituteValues(parsedFormula, variables);

        Check.ifFirstMinus(parsedFormula);
        Check.ifNoOperators(parsedFormula);

        System.out.print(formula + " = ");

        while (parsedFormula.size() > 1) {
            try {
                ArrayList<String> expressionInTheParentheses = new ArrayList<>();
                //this whole branch is for calculations in parentheses
                if (parsedFormula.lastIndexOf("(") != -1) {
                    int indexOpened = parsedFormula.lastIndexOf("(");
                    int indexClosed = 0;
                    for (int i = indexOpened; i < parsedFormula.size(); i++)
                        if (parsedFormula.get(i).equals(")")) {
                            indexClosed = i;
                            break;
                        }
                    for (int i = indexOpened + 1; i < indexClosed; i++)
                        expressionInTheParentheses.add(parsedFormula.get(i));

                    while (!parsedFormula.get(indexOpened + 2).equals(")"))
                        parsedFormula.remove(indexOpened + 1);

                    while (expressionInTheParentheses.size() > 1)
                        result = operations(expressionInTheParentheses);
                    Parsing.changeParsedFormula(parsedFormula, indexOpened + 1, result);
                    //for functions
                    if (indexOpened != 0 && parsedFormula.get(indexOpened - 1).length() > 1) {
                        result = functions(parsedFormula.get(indexOpened - 1), expressionInTheParentheses.get(0));
                        parsedFormula.set(indexOpened - 1, String.valueOf(result));
                        parsedFormula.remove(indexOpened);
                    }
                } else result = operations(parsedFormula); //simple calculations without parentheses

            } catch (Exception e) {
                System.out.println("\nError with signs");
                System.exit(1);
            }
        }
        System.out.print(result);
    }

    /**
     * This method checks for operators in the expression and calls the appropriate method
     *
     * @param parsedFormula two operand and one operator
     * @return result of calculations
     */
    public static double operations(ArrayList<String> parsedFormula) {
        double result = 0;
        if (parsedFormula.contains("^"))
            result = Operations.exponentiation(parsedFormula);

        else if (parsedFormula.contains("/"))
            result = Operations.division(parsedFormula);

        else if (parsedFormula.contains("*"))
            result = Operations.multiply(parsedFormula);

        else if (parsedFormula.contains("-"))
            result = Operations.subtract(parsedFormula);

        else if (parsedFormula.contains("+"))
            result = Operations.add(parsedFormula);

        return result;
    }

    /**
     * This method checks for functions in the expression and calls the appropriate method
     *
     * @param function                   function name
     * @param expressionInTheParentheses function argument
     * @return result of calculations
     */
    public static double functions(String function, String expressionInTheParentheses) {
        double argument = Double.parseDouble(expressionInTheParentheses);
        try {
            return switch (function) {
                case "acos" -> Functions.acos(argument);
                case "asin" -> Functions.asin(argument);
                case "atan" -> Functions.atan(argument);
                case "sin" -> Functions.sin(argument);
                case "cos" -> Functions.cos(argument);
                case "tan" -> Functions.tan(argument);
                case "log10" -> Functions.log10(argument);
                case "log2" -> Functions.log2(argument);
                case "sqrt" -> Functions.sqrt(argument);
                default -> throw new IllegalStateException();
            };
        } catch (Exception e) {
            System.out.println("Function " + function + " isn't supported");
            System.exit(1);
            return argument;
        }
    }
}