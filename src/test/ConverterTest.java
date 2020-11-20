package test;

import model.Converter;
import model.MemoryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConverterTest {
    
	Converter convert = new Converter();
	MemoryUnit memory = new MemoryUnit();
	
	//test Binary to Hex
	@Test
	void testBinToHex() {
		String binaryPositive = "0011110"; // = 22 (decimal)
		String binaryZero = "0000";
		String binaryNegative1 = "11111010"; // = -6 (decimal) *eight characters long
		String binaryNegative2 = "111010"; // = -6 (decimal) *six characters long
		assertEquals(convert.binToHex(binaryPositive),"1e");
		assertEquals(convert.binToHex(binaryZero),"0");
		assertEquals(convert.binToHex(binaryNegative1),"fa");	// This one passes!
		assertEquals(convert.binToHex(binaryNegative2),"fa");	// TODO Fix: Extend binary sign bit so that length is divisible by 4, then convert to hex to get the correct 2's compliment value (like the test above)
	}

	@Test
	void testBinToHexSetLength() {
		String binaryPositive = "01010";	// = 10 (decimal)
		String binaryZero = "0";			// = 0 (decimal)
		String binaryNegative = "10110";	// = -10 (decimal)
		assertEquals(convert.binToHexSetLength(binaryPositive, 3), "00a");
		//assertEquals(convert.binToHexSetLength(binaryPositive, 1), "0a"); //TODO Fix: (for Angela) Fix so that the trimmer doesn't accidentally trim off the sign bit if the next bit's sign bit doesn't match the same bit as the last sign extended bit
		assertEquals(convert.binToHexSetLength(binaryZero, 4), "0000"); 	// ^ Ex: SignBit = 1? Trimming F away from next char 1-7 not allowed (0000 - 0111). SignBit = 0? Trimming 0 away from next char 8-f not allowed (1000 - 1111). *Can only trim leading F or 0 bits.
		assertEquals(convert.binToHexSetLength(binaryNegative, 4), "fff6"); //TODO Fix: (for Angela): make sure that leading zero bit in binary extends trough whole hex character (multiple of four). Don't let "1 0110" extend to "0001 0110" then later bit extend to "1111 0001 01110"
		assertEquals(convert.binToHexSetLength(binaryNegative, 1), "f6");	//TODO Fix: (for Angela): same as above ^
	}
	
	//test Binary to Decimal
	@Test
	void testBinToDec() {
		String binaryPositive = "00001010";	// = 10 (decimal)
		String binaryZero = "00000";		// = 0 (decimal)
		String binaryNegative = "111010";	// = -6 (decimal)
		assertEquals(convert.binToDec(binaryPositive), 10);
		assertEquals(convert.binToDec(binaryZero), 0);
		assertEquals(convert.binToDec(binaryNegative), -6); //TODO Fix: Transform the negative 2's compliment binary bits to positive 2's compliment, find the decimal value, then return (-1 * positiveDecimalValue) to return negative numbers.
	}
    
	//test Decimal to Binary
	@Test
	void testDecToBin() {
		int decimalPositive = 10;
		int decimalZero = 0;
		int decimalNegative1 = -6;
		int decimalNegative2 = -10;
		assertEquals(convert.decToBin(decimalPositive), "01010"); //TODO Fix: If decimal is positive, make sure binary returns with a 0 at the front.
		assertEquals(convert.decToBin(decimalZero), "0");
		assertEquals(convert.decToBin(decimalNegative1), "11111111111111111111111111111010");
		assertEquals(convert.decToBin(decimalNegative2), "11111111111111111111111111110110");
	}
	
	//test Hex to Binary
	@Test
	void testHexToBin() {
		String hexcodePositive = "00Ff";
		String hexcodeZero = "0000";
		String hexcodeNegative = "FF41";
		assertEquals(convert.hexToBin(hexcodePositive), "011111111"); //TODO Fix: If 2's compliment hex is positive, make sure 2's compliment binary returns with a 0 at the front.
		assertEquals(convert.hexToBin(hexcodeZero), "0");
		assertEquals(convert.hexToBin(hexcodeNegative), "1111111101000001");
	}
	
	//test Hex to Decimal
	@Test
	void testHexToDec() {
		String hexcodePositive = "00Ff";
		String hexcodeZero = "0000";
		String hexcodeNegative1 = "Ff";
		String hexcodeNegative2 = "ff0f";
		assertEquals(convert.hexToDec(hexcodePositive), 255);
		assertEquals(convert.hexToDec(hexcodeZero), 0);
		assertEquals(convert.hexToDec(hexcodeNegative1), -1);		// TODO Fix: If sign bit is negative, can do hex -> binary, then invert the binary to a positive binary value, then find the positive decimal, then return (-1 * positiveDecimalValue)
		assertEquals(convert.hexToDec(hexcodeNegative2), -241);	// TODO FIX: Same as above ^
	}
	
	//test Decimal to Hex
	@Test 
	void testDecToHex() {
		int decimalPostive = 10;
		int decimalZero = 0;
		int decimalNegative = -6;
		assertEquals(convert.decToHex(decimalPostive), "0a"); // TODO: If decimal is positive, make sure hex value retains front sign bit of "0" to retain 2's compliment. 10(decimal) in unsigned binary is "1010", which if in 2's compliment would be mistaken for -6(decimal).
		assertEquals(convert.decToHex(decimalZero), "0");
		assertEquals(convert.decToHex(decimalNegative), "fffffffa");
	}

	@Test
	void testDecToHexSetLength() {
		int decimalPositive = 10;
		int decimalZero = 0;
		int decimalNegative = -10;	// = 1111 0110 (binary)
		assertEquals(convert.decToHexSetLength(decimalPositive, 3), "00a");
		assertEquals(convert.decToHexSetLength(decimalPositive, 1), "0a"); //TODO Fix: (for Angela) same as below v
		assertEquals(convert.decToHexSetLength(decimalZero, 4), "0000");
		assertEquals(convert.decToHexSetLength(decimalNegative, 4), "fff6");
		assertEquals(convert.decToHexSetLength(decimalNegative, 1), "f6"); //TODO Fix: (for Angela) Fix so that the trimmer doesn't accidentally trim off the sign bit if the next bit's sign bit doesn't match the same bit as the last sign extended bit.
	}																						   // ^ Ex: SignBit = 1? Trimming F away from next char 1-7 not allowed (0000 - 0111). SignBit = 0? Trimming 0 away from next char 8-f not allowed (1000 - 1111). *Can only trim leading F or 0 bits.

	@Test
	void testMemoryUnit() {
		memory.storeAt(1, "The End");
		assertEquals(memory.getDataAt(1), "The End");
	}
}
