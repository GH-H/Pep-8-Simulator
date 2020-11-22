package test;

import model.Converter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the methods in the Converter class that are meant to translate 2's complement binary between
 * 2's complement hexadecimal and between decimal values.
 *
 * @version 21 November 2020
 */
class ConverterTest {
	
	/**
	 * Tests conversions of 2's complement binary to 2's complement hexadecimal.
	 */
	@Test
	void testBinToHex() {
		String binaryPositive8Bit = "00011110";
		String binaryPositive16Bit = "0110000111100101";
		String binaryZero8Bit = "00000000";
		String binaryZero16Bit = "0000000000000000";
		String binaryNegative8Bit = "11111010";
		String binaryNegative16Bit = "1111010110101011";

		assertEquals(Converter.binToHex(binaryPositive8Bit),"1E");
		assertEquals(Converter.binToHex(binaryPositive16Bit),"61E5");

		assertEquals(Converter.binToHex(binaryZero8Bit),"00");
		assertEquals(Converter.binToHex(binaryZero16Bit),"0000");

		assertEquals(Converter.binToHex(binaryNegative8Bit),"FA");
		assertEquals(Converter.binToHex(binaryNegative16Bit),"F5AB");

	}

	/**
	 * Tests conversions of 2's complement binary to decimal.
	 */
	@Test
	void testBinToDec() {
		String binaryPositive8Bit = "00011110";
		String binaryPositive16Bit = "0110000111100101";
		String binaryZero8Bit = "00000000";
		String binaryZero16Bit = "0000000000000000";
		String binaryNegative8Bit = "11111010";
		String binaryNegative16Bit = "1111010110101011";

		assertEquals(Converter.binToDec(binaryPositive8Bit), 30);
		assertEquals(Converter.binToDec(binaryPositive16Bit), 25061);

		assertEquals(Converter.binToDec(binaryZero8Bit), 0);
		assertEquals(Converter.binToDec(binaryZero16Bit), 0);

		assertEquals(Converter.binToDec(binaryNegative8Bit), -6);
		assertEquals(Converter.binToDec(binaryNegative16Bit), -2645);
	}

    /**
     * Tests conversions from decimal to 2's complement binary.
	 */
	@Test
	void testDecToBin() {
		int decimalPositive8Bit = 30;
		int decimalPositive16Bit = 25061;
		int decimalZero = 0;
		int decimalNegative8Bit = -6;
		int decimalNegative8Bit2 = -10;
		int decimalNegative16Bit = -2645;

		assertEquals(Converter.decToBin(decimalPositive8Bit,8), "00011110");
		assertEquals(Converter.decToBin(decimalPositive8Bit,16), "0000000000011110");
		assertEquals(Converter.decToBin(decimalPositive16Bit,8), "11100101");
		assertEquals(Converter.decToBin(decimalPositive16Bit,16), "0110000111100101");

		assertEquals(Converter.decToBin(decimalZero,8), "00000000");
		assertEquals(Converter.decToBin(decimalZero,16), "0000000000000000");

		assertEquals(Converter.decToBin(decimalNegative8Bit,8), "11111010");
		assertEquals(Converter.decToBin(decimalNegative8Bit,16), "1111111111111010");
		assertEquals(Converter.decToBin(decimalNegative8Bit2,8), "11110110");
		assertEquals(Converter.decToBin(decimalNegative8Bit2,16), "1111111111110110");
		assertEquals(Converter.decToBin(decimalNegative16Bit,8), "10101011");
		assertEquals(Converter.decToBin(decimalNegative16Bit,16), "1111010110101011");
	}

	/**
	 * Tests conversions from 2's complement hexadecimal to 2's complement binary.
	 */
	@Test
	void testHexToBin() {
		String hexcodePositive2Char = "7e";
		String hexcodePositive4Char = "2C47";
		String hexcodeZero2Char = "00";
		String hexcodeZero4Char = "0000";
		String hexcodeNegative2Char = "b2";
		String hexcodeNegative4Char = "aF3d";

		assertEquals(Converter.hexToBin(hexcodePositive2Char), "01111110");
		assertEquals(Converter.hexToBin(hexcodePositive4Char), "0010110001000111");

		assertEquals(Converter.hexToBin(hexcodeZero2Char), "00000000");
		assertEquals(Converter.hexToBin(hexcodeZero4Char), "0000000000000000");

		assertEquals(Converter.hexToBin(hexcodeNegative2Char), "10110010");
		assertEquals(Converter.hexToBin(hexcodeNegative4Char), "1010111100111101");
	}

	/**
	 * Tests conversions from 2's complement hexadecimal to decimal.
	 */
	@Test
	void testHexToDec() {
		String hexcodePositive2Char = "7e";
		String hexcodePositive4Char = "2C47";
		String hexcodeZero2Char = "00";
		String hexcodeZero4Char = "0000";
		String hexcodeNegative2Char = "b2";
		String hexcodeNegative4Char = "aF3d";

		assertEquals(Converter.hexToDec(hexcodePositive2Char), 126);
		assertEquals(Converter.hexToDec(hexcodePositive4Char), 11335);

		assertEquals(Converter.hexToDec(hexcodeZero2Char), 0);
		assertEquals(Converter.hexToDec(hexcodeZero4Char), 0);

		assertEquals(Converter.hexToDec(hexcodeNegative2Char), -78);
		assertEquals(Converter.hexToDec(hexcodeNegative4Char), -20675);
	}

	/**
	 * Tests conversions from decimal to 2's complement hexadecimal.
	 */
	@Test 
	void testDecToHex() {
		int decimalPositive8Bit = 30;
		int decimalPositive16Bit = 25061;
		int decimalZero = 0;
		int decimalNegative8Bit = -6;
		int decimalNegative8Bit2 = -10;
		int decimalNegative16Bit = -2645;

		assertEquals(Converter.decToHex(decimalPositive8Bit,2), "1E");
		assertEquals(Converter.decToHex(decimalPositive8Bit,4), "001E");
		assertEquals(Converter.decToHex(decimalPositive16Bit,2), "E5");
		assertEquals(Converter.decToHex(decimalPositive16Bit,4), "61E5");

		assertEquals(Converter.decToHex(decimalZero,2), "00");
		assertEquals(Converter.decToHex(decimalZero,4), "0000");

		assertEquals(Converter.decToHex(decimalNegative8Bit,2), "FA");
		assertEquals(Converter.decToHex(decimalNegative8Bit,4), "FFFA");
		assertEquals(Converter.decToHex(decimalNegative8Bit2,2), "F6");
		assertEquals(Converter.decToHex(decimalNegative8Bit2,4), "FFF6");
		assertEquals(Converter.decToHex(decimalNegative16Bit,2), "AB");
		assertEquals(Converter.decToHex(decimalNegative16Bit,4), "F5AB");

	}
}
