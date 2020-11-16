package model;
import java.math.BigInteger;

/**
 * This class was designed to convert any given
 * code (For now only binary, hex and decimal)
 *
 */
public class Converter {

    private String input;

    public Converter () {

    }

    //return Binary to Hexcode!
    public static String binToHex(String input) {
        int decimal = Integer.parseInt(input,2);
        return Integer.toString(decimal,16);
    }

    //return Binary to Decimal!
    public static int binToDec(String input) {
        int decimal = Integer.parseInt(input,2);
        return decimal;
    }

    //return Decimal to Binary!
    public static int decToBin(int input) {
        String Binary = Integer.toBinaryString(input);
        return Integer.valueOf(Binary);
    }

    //return Hexcode to Binary!
    public static String hexToBin(String input) {
        return new BigInteger(input, 16).toString(2);
    }

    //return Hexcode to Decimal!
    public static int hexToDec(String input) {
        int decimal= Integer.parseInt(input,16);
        return decimal;
    }
    //return Decimal to Hexcode!
    public static String decToHex(int input) {
        String Hexcode = Integer.toHexString(input);
        return Hexcode;
    }
}

