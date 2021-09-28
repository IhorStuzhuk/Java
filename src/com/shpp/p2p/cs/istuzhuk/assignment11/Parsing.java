package com.shpp.p2p.cs.istuzhuk.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for parsing the formula
 */
public class Parsing {
    /**
     * This method parses entered arguments and puts them in the HashMap
     *
     * @param variables hashmap for future reference
     * @param args      entered arguments by user
     * @return variables with values
     */
    static HashMap<String, Double> parseArguments(HashMap<String, Double> variables, String[] args) {
        for (int i = 1; i < args.length; i++) {
            String value = args[i].replaceAll(" ", "").replaceAll(",", ".");
            if (value.contains("=")) {
                int index = value.indexOf("=");
                if (index > 0 && index != value.length() - 1) {//if "=" sign isn't first and last in the string
                    // if the name is more than 1 character or value is more than 1 figure
                    if (index - 1 > 0 || index + 1 < value.length())
                        variables.put(value.substring(0, index), Double.parseDouble(value.substring(index + 1)));
                    else
                        variables.put(value, Double.parseDouble(value.substring(index + 1)));
                } else {
                    System.out.println("Please enter the variables correctly (in quotes or without spaces)");
                    System.exit(1);
                }
            }
        }
        return variables;
    }

    /**
     * This method parses entered formula into separate lines
     *
     * @param parsedFormula list for keeping
     * @param formula       entered formula by user
     * @return parsedFormula
     */
    static ArrayList<String> parseFormula(ArrayList<String> parsedFormula, String formula) {
        // Split by place where there is no number on the left or no number on the right.
        for (String element : formula.split("(?<!\\d)|(?!\\d)"))
            parsedFormula.add(element);
        parsedFormula = uniteDoubleValues(parsedFormula);

        parsedFormula = uniteFunctionsName(parsedFormula);
        return parsedFormula;
    }

    /**
     * This method checks the parsed formula for functions to the left of the opening parenthesis
     *
     * @param parsedFormula parsed formula
     * @return parsedFormula
     */
    private static ArrayList<String> uniteFunctionsName(ArrayList<String> parsedFormula) {
        for (int i = 0; i < parsedFormula.size(); i++)
            if (parsedFormula.get(i).equals("(")) {
                parsedFormula = Check.checkOnFunction(parsedFormula, i);
            }
        return parsedFormula;
    }

    /**
     * This method unites whole and fractional part for double values
     *
     * @param parsedFormula parsedFormula
     * @return finished parsedFormula
     */
    static ArrayList<String> uniteDoubleValues(ArrayList<String> parsedFormula) {
        //after parsing double values were parsed
        //so we need to unite them back
        for (int i = 0; i < parsedFormula.size(); i++)
            if (parsedFormula.contains(".")) {
                int pointIndex = parsedFormula.indexOf(".");
                String unite = parsedFormula.get(pointIndex - 1) + "." + parsedFormula.get(pointIndex + 1);
                parsedFormula.set(pointIndex - 1, unite);
                parsedFormula.remove(pointIndex + 1);
                parsedFormula.remove(pointIndex);
            }
        return parsedFormula;
    }

    /**
     * This method replaces variables with their corresponding values
     *
     * @param parsedFormula parsed formula
     * @param variables     entered variables by user
     * @return formula with values
     */
    public static ArrayList<String> substituteValues(ArrayList<String> parsedFormula, HashMap<String, Double> variables) {
        HashMap<String, Double> variablesCopy = new HashMap<>();
        for (Map.Entry<String, Double> variableName : variables.entrySet()) {
            variablesCopy.put(variableName.getKey(), variableName.getValue());
            for (int i = 0; i < parsedFormula.size(); i++)
                if (parsedFormula.get(i).equals(variableName.getKey())) {
                    parsedFormula.set(i, String.valueOf(variableName.getValue()));
                    variablesCopy.remove(variableName.getKey(), variableName.getValue());
                }
        }
        if (variablesCopy.size() > 0) {
            for (Map.Entry<String, Double> variableName : variablesCopy.entrySet())
                System.out.println("\nVariable " + "'" + variableName.getKey() + "'" + " wasn't founded in the expression");
            System.exit(1);
        }
        return parsedFormula;
    }

    /**
     * This method shortens the list of operations by deleting already performed
     *
     * @param parsedFormula parsedFormula
     * @param index         index of operator sign
     * @param result        calculation result
     */
    static void changeParsedFormula(ArrayList<String> parsedFormula, int index, double result) {
        parsedFormula.set(index - 1, String.valueOf(result));
        parsedFormula.remove(index + 1);
        parsedFormula.remove(index);
    }
}