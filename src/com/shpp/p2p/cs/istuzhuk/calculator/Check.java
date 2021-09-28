package com.shpp.p2p.cs.istuzhuk.calculator;

import java.util.ArrayList;

/**
 * This class is responsible for various checks in the formula
 */
public class Check {
    /**
     * This method checks if there is a minus at the beginning of the formula
     * and if true then connects it to the next value in the list
     *
     * @param parsedFormula parsedFormula
     */
    public static void ifFirstMinus(ArrayList<String> parsedFormula) {
        if (parsedFormula.get(0).equals("-") && !parsedFormula.get(1).equals("(")) {
            parsedFormula.set(0, "-" + parsedFormula.get(1));
            parsedFormula.remove(1);
        } else if (parsedFormula.get(0).equals("-") && parsedFormula.get(1).equals("(")) {
            parsedFormula.set(2, "-" + parsedFormula.get(2));
            parsedFormula.remove(0);
        }
    }

    /**
     * This method checks if the formula contains operators
     * if false then stops the program
     *
     * @param parsedFormula parsedFormula
     */
    public static void ifNoOperators(ArrayList<String> parsedFormula) {
        if ((!parsedFormula.contains("*")) && (!parsedFormula.contains("/"))
                && (!parsedFormula.contains("+")) && (!parsedFormula.contains("-"))
                && !parsedFormula.contains("^") && ifNoFunctions(parsedFormula)) {
            System.out.println("Please enter any operators or functions supported by the calculator");
            System.exit(1);
        }
    }

    /**
     * This method checks if the parsed formula contains at least one of these functions
     *
     * @param parsedFormula parsed formula
     * @return true/false
     */
    public static boolean ifNoFunctions(ArrayList<String> parsedFormula) {
        ArrayList<String> functions = new ArrayList<>();
        functions.add("acos");
        functions.add("asin");
        functions.add("atan");
        functions.add("sin");
        functions.add("cos");
        functions.add("tan");
        functions.add("log10");
        functions.add("log2");
        functions.add("sqrt");
        for (String element : parsedFormula)
            for (String fun : functions)
                if (element.equals(fun))
                    return false;
        return true;
    }

    /**
     * This method checks if a minus is after any operator in the formula
     * and if true, then connects it to the next value in the list
     *
     * @param index         of minus in the formula
     * @param parsedFormula parsedFormula
     */
    public static void ifNextMinus(int index, ArrayList<String> parsedFormula) {
        if (parsedFormula.get(index + 1).equals("-")) {
            parsedFormula.set(index + 1, "-" + parsedFormula.get(index + 2));
            parsedFormula.remove(index + 2);
        }
    }

    /**
     * This method is called when subtracting
     * and checks the position of the plus sign relative to the minus signs. And also 2 minuses in a row
     *
     * @param parsedFormula parsedFormula
     * @param index         minus index
     * @return if signs were changed
     */
    public static boolean changeSigns(ArrayList<String> parsedFormula, int index) {
        if (index == 0) {
            parsedFormula.set(0, "-" + parsedFormula.get(index + 1));
            parsedFormula.remove(index + 1);
            return true;
        } else if (parsedFormula.get(index + 1).equals("+")) {
            parsedFormula.remove(index + 1);
            return true;
        } else if (index > 0 && parsedFormula.get(index - 1).equals("+")) {
            parsedFormula.remove(index - 1);
            return true;
        } else if (parsedFormula.get(index + 1).equals("-")) {
            parsedFormula.set(index, "+");
            parsedFormula.remove(index + 1);
            return true;
        } else if (index > 0 && parsedFormula.get(index - 1).equals("(")) {
            parsedFormula.set(index, "-" + parsedFormula.get(index + 1));
            parsedFormula.remove(index + 1);
            return true;
        } else if (parsedFormula.get(index + 1).equals("(")) {
            parsedFormula.set(index + 2, "-" + parsedFormula.get(index + 2));
            parsedFormula.remove(index);
            return true;
        }
        return false;
    }

    /**
     * This method checks if the parentheses are correctly placed
     *
     * @param formula formula by user
     * @return true/false
     */
    public static boolean parenthesesAreCorrect(String formula) {
        int countOpenedParentheses = 0;
        int countClosedParentheses = 0;
        for (int i = 0; i < formula.length(); i++) {
            char c = formula.charAt(i);
            if (c == '(')
                countOpenedParentheses++;
            else if (c == ')')
                countClosedParentheses++;
        }
        return countOpenedParentheses == countClosedParentheses;
    }

    /**
     * This method checks the formula for functions and collects the function name symbol by symbol
     *
     * @param parsedFormula parsed formula
     * @param indexOpened   index opened parenthesis
     * @return parsed formula with functions, if any
     */
    public static ArrayList<String> checkOnFunction(ArrayList<String> parsedFormula, int indexOpened) {
        StringBuilder function = new StringBuilder();
        int i = indexOpened - 1;
        int indexLastLetter = 0;

        while (i >= 0 && Character.isLetterOrDigit(parsedFormula.get(i).charAt(0))) {
            function.insert(0, parsedFormula.get(i));
            i--;
            indexLastLetter++;
        }
        if (function.length() > 0) {
            indexLastLetter = indexOpened - indexLastLetter;
            parsedFormula.set(indexLastLetter, String.valueOf(function));

            while (!parsedFormula.get(indexLastLetter + 1).equals("("))
                parsedFormula.remove(indexLastLetter + 1);
        }
        return parsedFormula;
    }
}