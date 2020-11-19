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
		String binary = "1010";
		String binary2 = "0110";
		assertEquals(convert.binToHex(binary),"a");
		assertEquals(convert.binToHex(binary2),"6");
	}

	@Test
	void testBinToHexLeadingZeroes() {
		String binaryPositive = "01010";	// = 10 (decimal)
		String binaryNegative = "1010";		// = -6 (decimal)
		assertEquals(convert.binToHexSetLength(binaryPositive, 3), "00a");
		assertEquals(convert.binToHexSetLength(binaryNegative, 3), "ffa");
	}
	
	//test Binary to Decimal
	@Test
	void testBinToDec() {
		String Binary = "1010";
		assertEquals(convert.binToDec(Binary), 10);
	}
    
	//test Decimal to Binary
	@Test
	void testDecToBin() {
		int Decimal = 10;
		assertEquals(convert.decToBin(Decimal), "1010");
	}
	
	//test Hex to Binary
	@Test
	void testHexToBin() {
		String hexcode = "00Ff";
		String hexcode2 = "0041";
		assertEquals(convert.hexToBin(hexcode), "11111111");
		assertEquals(convert.hexToBin(hexcode2), "1000001");
	}
	
	//test Hex to Decimal
	@Test
	void testHexToDec() {
		String hexcode = "00FF";
		String hexcode2 = "fff6";
		assertEquals(convert.hexToDec(hexcode), 255);
		assertEquals(convert.hexToDec(hexcode2), -10); //TODO: Fix to work with 2's compliment.
	}
	
	//test Decimal to Hex
	@Test 
	void testDecToHex() {
		int decimal = 10;
		int decimal2 = -10;
		assertEquals(convert.decToHex(decimal), "a");
		assertEquals(convert.decToHex(decimal2), "fffffff6");
	}

	@Test
	void testDecToHexLeadingZeroes() {
		int decimalPositive = 11;
		int decimalNegative = -11;
		assertEquals(convert.decToHexSetLength(decimalPositive, 3), "00b");
		assertEquals(convert.decToHexSetLength(decimalNegative, 4), "fff5");
		assertEquals(convert.decToHexSetLength(decimalNegative, 1), "5"); //TODO: (Angela) Fix so that this method doesn't trim 'F' if the adjacent ASCII character doesn't start with the same sign.
	}

	@Test
	void testMemoryUnit() {
		memory.storeAt(1, "The End");
		assertEquals(memory.getDataAt(1), "The End");
	}
}
