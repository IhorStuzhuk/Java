package com.shpp.p2p.cs.istuzhuk.assignment11;

import java.util.ArrayList;

/**
 * This class contains various mathematical operations
 */
public class Operations {
    /**
     * This method raises a number to a power
     *
     * @param parsedFormula parsedFormula
     * @return result
     */
    public static double exponentiation(ArrayList<String> parsedFormula) {
        double result;
        int index = parsedFormula.indexOf("^");
        Check.ifNextMinus(index, parsedFormula);

        result = Math.pow(Double.parseDouble(parsedFormula.get(index - 1)),
                Double.parseDouble(parsedFormula.get(index + 1)));
        Parsing.changeParsedFormula(parsedFormula, index, result);
        return result;
    }

    /**
     * This method divides two numbers
     *
     * @param parsedFormula parsedFormula
     * @return result
     */
    public static double division(ArrayList<String> parsedFormula) {
        double result;
        int index = parsedFormula.indexOf("/");
        Check.ifNextMinus(index, parsedFormula);

        result = Double.parseDouble(parsedFormula.get(index - 1)) / Double.parseDouble(parsedFormula.get(index + 1));
        Parsing.changeParsedFormula(parsedFormula, index, result);
        return result;
    }

    /**
     * This method multiplies two numbers
     *
     * @param parsedFormula parsedFormula
     * @return result
     */
    public static double multiply(ArrayList<String> parsedFormula) {
        double result;
        int index = parsedFormula.indexOf("*");
        Check.ifNextMinus(index, parsedFormula);

        result = Double.parseDouble(parsedFormula.get(index - 1)) * Double.parseDouble(parsedFormula.get(index + 1));
        Parsing.changeParsedFormula(parsedFormula, index, result);
        return result;
    }

    /**
     * This method subtracts two numbers
     *
     * @param parsedFormula parsedFormula
     * @return result
     */
    public static double subtract(ArrayList<String> parsedFormula) {
        double result = 0;
        int index = parsedFormula.indexOf("-");
        int lastIndex = parsedFormula.lastIndexOf("-");

        if (Check.changeSigns(parsedFormula, index))
            return result; //return to update the changed list

        if ((lastIndex - 2 > 0) && (parsedFormula.get(lastIndex - 2).equals("-"))) {
            result = Double.parseDouble(parsedFormula.get(lastIndex - 1)) +
                    Double.parseDouble(parsedFormula.get(lastIndex + 1));
            parsedFormula.set(lastIndex - 2, String.valueOf(result));
            parsedFormula.remove(lastIndex + 1);
            parsedFormula.remove(lastIndex - 1);
            parsedFormula.remove(lastIndex);
        } else {
            result = Double.parseDouble(parsedFormula.get(index - 1)) -
                    Double.parseDouble(parsedFormula.get(index + 1));
            Parsing.changeParsedFormula(parsedFormula, index, result);
        }
        return result;
    }

    /**
     * This method adds two numbers
     *
     * @param parsedFormula parsedFormula
     * @return result
     */
    public static double add(ArrayList<String> parsedFormula) {
        double result;
        int index = parsedFormula.indexOf("+");
        result = Double.parseDouble(parsedFormula.get(index - 1)) + Double.parseDouble(parsedFormula.get(index + 1));
        Parsing.changeParsedFormula(parsedFormula, index, result);
        return result;
    }
}