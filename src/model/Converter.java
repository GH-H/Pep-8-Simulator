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

    /**
     * Translates a binary value to a hexadecimal String representation. The hexadecimal value is in 2's compliment,
     * and will try to force itself to a set length specified in the parameters. If a hex value character cannot be
     * trimmed shorter without damaging the sign bit, it will be trimmed to as short as the value can be without losing
     * its sign bit.
     *
     * @param theBinary The binary value that should be translated to a hexadecimal String form.
     * @param totalStringLength The total number of characters the hexadecimal string should eiter extend or trim
     *                          itself to fit, while retaining its 2's compliment sign bit.
     * @return The hexadecimal value of a binary representation that either matches the length of bits specified in
     *         the totalStringLength parameter, or as short as it can be without damaging the value's sign bit.
     */
    public static String binToHexSetLength(final String theBinary, final int totalStringLength) {
        String hexValue = binToHex(theBinary);
        char hexSignExtender = ' ';
        try {
            if (theBinary.charAt(0) == '0') {
                hexSignExtender = '0';  // Set positive hex extender to '0'
            } else {
                hexSignExtender = 'f';  // Set negative hex extender to 'f'
            }
            if (hexValue.length() > totalStringLength) {    // If the hex value should be trimmed.
                int startLength = hexValue.length();
                for (int i = 0; i < startLength - totalStringLength; i++) {
                    if (hexValue.charAt(0) == hexSignExtender) { // Trim if it's just the sign extend value being cut.
                        hexValue = hexValue.substring(1);
                    } else {    // Else throw exception if trim would harm hex value.
                        throw new IllegalArgumentException("Inputted decimal value has a longer hexadecimal number than the desired " +
                                "total String length. Don't want to slice hexadecimal value data to fit smaller total String length.");
                    }
                }
            } else if (hexValue.length() < totalStringLength) { // If hex value should be extended.
                int hexStartLength = hexValue.length();
                for (int i = 0; i < totalStringLength - hexStartLength; i++) { // Extend by the sign extender value.
                    hexValue = hexSignExtender + hexValue;
                }
            } // else return hexValue as-is since it is already the proper total String length.
        } catch (Exception e) {
            System.out.println(e);
        }
        return hexValue;
    }

    //return Binary to Decimal!
    public static int binToDec (String input) {
        int decimal = Integer.parseInt(input,2);
        return decimal;
    }

    //return Decimal to Binary!
    public static String decToBin(int input) {
        String Binary = Integer.toBinaryString(input);
        return Binary;
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

    /**
     * Translates a decimal value to a hexadecimal String representation. The hexadecimal value is in 2's compliment,
     * and will try to force itself to a set length specified in the parameters. If a hex value character cannot be
     * trimmed shorter without damaging the sign bit, it will be trimmed to as short as the value can be without losing
     * its sign bit.
     *
     * @param theDecimal The decimal value that should be translated to a hexadecimal String form.
     * @param totalStringLength The total number of characters the hexadecimal string should eiter extend or trim
     *                          itself to fit, while retaining its 2's compliment sign bit.
     * @return The hexadecimal value of a decimal representation that either matches the length of bits specified in
     *         the totalStringLength parameter, or as short as it can be without damaging the value's sign bit.
     */
    public static String decToHexSetLength(final int theDecimal, final int totalStringLength) {
        String hexValue = decToHex(theDecimal);
        char hexSignExtender = ' ';
        try {
            if (theDecimal >= 0) {
                hexSignExtender = '0'; // Set positive hex extender to '0'
            } else {
                hexSignExtender = 'f'; // Set negative hex extender to 'f'
            }
            if (hexValue.length() > totalStringLength) {    // If the hex value should be trimmed.
                int startLength = hexValue.length();
                for (int i = 0; i < startLength - totalStringLength; i++) {
                    if (hexValue.charAt(0) == hexSignExtender) { // Trim if it's just the sign extend value being cut.
                        hexValue = hexValue.substring(1);
                    } else {    // Else throw exception if trim would harm hex value.
                        throw new IllegalArgumentException("Inputted decimal value has a longer hexadecimal number than the desired " +
                                "total String length. Don't want to slice hexadecimal value data to fit smaller total String length.");
                    }
                }
            } else if (hexValue.length() < totalStringLength) { // If hex value should be extended.
                int hexStartLength = hexValue.length();
                for (int i = 0; i < totalStringLength - hexStartLength; i++) { // Extend by the sign extender value.
                    hexValue = hexSignExtender + hexValue;
                }
            } // else return hexValue as-is since it is already the proper total String length.
        } catch (Exception e) {
            System.out.println(e);
        }
        return hexValue;
    }
}

