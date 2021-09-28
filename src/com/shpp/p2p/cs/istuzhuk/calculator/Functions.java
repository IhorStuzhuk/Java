package com.shpp.p2p.cs.istuzhuk.calculator;

/**
 * This class contains various functions
 */
public class Functions {
    public static double acos(double argument) {
        if(argument > 1 || argument < 1) {
            System.out.println("The argument for the 'acos' function is greater than 1 or less than -1 ");
            System.exit(1);
        }
        return Math.acos(argument);
    }

    public static double asin(double argument) {
        if(argument > 1 || argument < 1) {
            System.out.println("The argument for the 'asin' function is greater than 1 or less than -1 ");
            System.exit(1);
        }
        return Math.asin(argument);
    }

    public static double atan(double argument) {
        return Math.atan(argument);
    }

    public static double log10(double argument) {
        if(argument < 1) {
            System.out.println("The argument for the 'log10' function is less than 1");
            System.exit(1);
        }
        return Math.log10(argument);
    }

    public static double log2(double argument) {
        if(argument < 1) {
            System.out.println("The argument for the 'log2' function is less than 1");
            System.exit(1);
        }
        return Math.log(argument) / Math.log(2);
    }

    public static double sqrt(double argument) {
        if(argument < 0) {
            System.out.println("The argument for the 'sqrt' function is less than 0");
            System.exit(1);
        }
        return Math.sqrt(argument);
    }

    public static double sin(double argument) {
        return Math.sin(argument);
    }

    public static double cos(double argument) {
        return Math.cos(argument);
    }

    public static double tan(double argument) {
        return Math.tan(argument);
    }
}