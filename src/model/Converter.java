package model;

import java.math.BigInteger;

import static java.lang.Integer.toBinaryString;

/**
 * This class was designed to convert any given 
 * code (For now only 2's complement binary, 2's complement hex and decimal).
 */
public class Converter {

	/**
     * Fake constructor that does nothing.
	 */
	public Converter() {
	}

	/**
	 * Converts a 2's complement, 8-or-16-bit long binary String to a 2's complement, 2-or-4-character long hexadecimal
	 * String respectively.
	 * The binary to be converted must be 8 bits or 16 bits long to accommodate Pep/8's memory and register sizes.
	 * The output hexadecimal value will be generated in uppercase characters.
	 *
	 * @param theBinary The 2's complement, 8-or-16-bit long binary String that should be converted to a String
	 *                  hexadecimal.
	 * @return A 2's complement, 2-or-4-character long hexadecimal String that correlates to the bit length of theBinary
	 * 		   parameter. The hexadecimal value will be generated in uppercase characters.
	 */
	public static String binToHex(final String theBinary) {
		String hexadecimalOutput = "";
		try {
			if (theBinary.length() != 8 && theBinary.length() != 16) {
				throw new IllegalArgumentException("theBinary parameter must be 8 or 16 bits long.");
			}
			int decimal = Integer.parseInt(theBinary,2);
			hexadecimalOutput = Integer.toString(decimal,16).toUpperCase();
			/* If the length of the generated hexadecimal value is too small (which is the case with positive
			 * 2's complement binary only), extend leading 0's to correlate with the input binary length
			 * (4 binary bits = 1 hex char).
			 */
			if (hexadecimalOutput.length() < (theBinary.length()/4)) {
				int hexStartLength = hexadecimalOutput.length();
				for (int i = 0; i < (theBinary.length()/4) - hexStartLength; i++) {
					hexadecimalOutput = "0" + hexadecimalOutput;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return hexadecimalOutput;
	}

	/**
	 * Converts an 8-bit or 16-bit-long 2's complement binary value to its decimal equivalent.
	 * The binary must be 8-bits or 16-bits long to accommodate Pep/8's default memory or register bit length sizes.
	 *
	 * @param theBinary The 8-bit or 16-bit-long 2's complement binary to be translated to a decimal value.
	 * @return The decimal value of the 2's complement binary that was inputted to the method.
	 */
	public static int binToDec (final String theBinary) {
		int decimalOutput = 0;
		try {
			int binaryLength = theBinary.length();
			// If theBinary is an invalid length to work with the Pep/8 program:
			if (binaryLength != 8 && binaryLength != 16) {
				throw new IllegalArgumentException("theBinary parameter must be exactly 8 or 16 bits long.");
			}

			// If theBinaryLength is a valid length to work with the Pep/8 program:
			if (binaryLength == 8) {
				if (theBinary.charAt(0) == '1') {	// If the returned decimal should be negative.
					decimalOutput = -128 + Integer.parseInt(theBinary.substring(1, theBinary.length()), 2);
				} else {	// If the returned decimal should be positive
					decimalOutput = Integer.parseInt(theBinary, 2);
				}
			} else if (binaryLength == 16) {
				if (theBinary.charAt(0) == '1') {	// If the returned decimal should be negative.
					decimalOutput = -32768 + Integer.parseInt(theBinary.substring(1, theBinary.length()), 2);
				} else {	// If the returned decimal should be positive.
					decimalOutput = Integer.parseInt(theBinary, 2);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return decimalOutput;
	}

	/**
	 * Converts a decimal value to a String representation of 2's complement binary, with a set return length of the
	 * binary value.
	 * The value of the 2's complement binary will overflow automatically if the the decimal is too large to fit
	 * in the set binary length. The method does not send a warning when this happens because Pep/8 will overflow
	 * bit values without warning as well.
	 *
	 * @param theDecimal The decimal value to translate into binary form.
	 * @param theBinaryReturnLength The total length of the binary to be generated from this method.
	 *                              Must be either 8 or 16.
	 * @return A String of the 2's complement binary value of theDecimal value whose length is equal to
	 * 		   theBinaryReturnLength.
	 */
	public static String decToBin(final int theDecimal, final int theBinaryReturnLength) {
		String binaryOutput = "";
		try {
			// If theReturnLength is invalid for Pep/8 data sizes:
			if (theBinaryReturnLength != 8 && theBinaryReturnLength != 16) {
				throw new IllegalArgumentException("theReturnLength parameter must be set to 8 or 16 bits long.");
			}

			// If theReturnLength is valid:
			binaryOutput = toBinaryString(theDecimal);
			int binStartLength = binaryOutput.length();
			/* If the length of the generated binary is too small (which is the case with positive values only),
			 * extend leading 0's to the desired return length.
			 */
			if (binStartLength < theBinaryReturnLength) {
				for (int i = 0; i < theBinaryReturnLength - binStartLength; i++) {
					binaryOutput = "0" + binaryOutput;
				}
			}
			/* If the length of the generated binary is too large (which is case w/ negative or large positive numbers),
			 * slice the binary to the desired length (not caring about overflow or losing data, as Pep/8 overflows
			 * binary without warning).
			 */
			else if (binStartLength > theBinaryReturnLength) {
				binaryOutput = binaryOutput.substring(binStartLength - theBinaryReturnLength); // Slice to only include last bits
			}
			// Else return binary as-is because it is already the perfect length in relation to theReturnLength argument.
		} catch (Exception e) {
			System.out.println(e);
		}
		return binaryOutput;
	}

	/**
	 * Converts a 2's complement, 2-or-4-character-long hexadecimal value to a 2's compeiment, 8-or-16-bit long binary value respectively.
	 * The hexadecimal value has to be 2 or 4 characters long to abide by Pep/8's memory or register bit sizes.
	 *
	 * @param theHexadecimal The 2's complement, 2-or-4-character-long hexadecimal value to be converted to binary.
	 * @return The 2's complement, 8-or-16-bit-long binary value that corresponds with the value inputted as theHexadecimal.
	 */
	public static String hexToBin(final String theHexadecimal) {
		String binaryOutput = "";
		try {
			// If theHexadecimal is an invalid length for Pep/8 data sizes:
			if (theHexadecimal.length() != 2 && theHexadecimal.length() != 4) {
				throw new IllegalArgumentException("theHexadecimal should be 2 or 4 characters long");
			}

			// If theHexadecimal is a valid length for Pep/8 data sizes:
			binaryOutput = new BigInteger(theHexadecimal, 16).toString(2);
			int binaryStartLength = binaryOutput.length();
			/*
			 * If the binaryOutput was generated too short because BigInteger doesn't add leading zeroes to positive
			 * hexadecimal conversions, append binaryOutput with zeroes until the binary is the correct length to
			 * correspond with theHexadecimal's binary bit length.
			 */
			if (binaryStartLength < theHexadecimal.length() * 4) {
				for (int i = 0; i < ((theHexadecimal.length() * 4) - binaryStartLength); i++) {
					binaryOutput = "0" + binaryOutput;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return binaryOutput;
	}

	/**
	 * Converts the 2's complement hexadecimal value to its corresponding decimal value.
	 * The hexadecimal to be converted should be 2 or 4 characters long to comply with Pep/8 memory or register
	 * data sizes.
	 *
	 * @param theHexadecimal The 2's complement hexadecimal value that should be translated to a decimal value.
	 *                       Should be a length of 2 or 4 characters.
	 * @return The decimal value of the 2's complement hexadecimal value.
	 */
	public static int hexToDec(final String theHexadecimal) {
		int decimalOutput = 0;
		try {
			// If theHexadecimal is an invalid length for Pep/8 data sizes:
			if (theHexadecimal.length() != 2 && theHexadecimal.length() != 4) {
				throw new IllegalArgumentException("theHexadecimal should be 2 or 4 characters long.");
			}

			// If theHexadecimal is a valid length for Pep/8 data sizes:
			String binary = hexToBin(theHexadecimal);
			decimalOutput = binToDec(binary);
		} catch (Exception e) {
			System.out.println(e);
		}
		return decimalOutput;
	}

    /**
     * Converts a decimal value to a 2's complement hexadecimal value that is either 2 or 4 characters long.
	 * The hexadecimal output must be specified to be 2 or 4 characters long to accommodate Pep/8's memory and register
	 * sizes.
	 * The value of the 2's complement hexadecimal will bit overflow automatically if the decimal is too large to fit
	 * in the hexadecimal's set character length. The method does not send a warning when this happens because Pep/8
	 * will overflow bit values without warning as well.
	 * The hexadecimal output will be generated in uppercase characters.
	 *
	 * @param theDecimal The decimal value to be converted to a 2's complement hexadecimal value.
	 * @param theHexadecimalReturnLength How many characters long the output hexadecimal value should be.
	 *                                   Must be set to 2 or 4.
	 * @return The 2's compliment hexadecimal value that was converted from theDecimal decimal value. The generated
	 * 		   length will be 2 or 4 depending on what argument was given for theHexadecimalReturnLength.
	 */
	public static String decToHex(final int theDecimal, final int theHexadecimalReturnLength) {
		String hexadecimalOutput = "";
		try {
			if (theHexadecimalReturnLength != 2 && theHexadecimalReturnLength != 4) {
				throw new IllegalArgumentException("theHexadecimalReturnLength must be set to 2 or 4.");
			}
			String binary = decToBin(theDecimal, (theHexadecimalReturnLength * 4));
			hexadecimalOutput = binToHex(binary);
		} catch (Exception e) {
			System.out.println(e);
		}
		return hexadecimalOutput;
	}
}