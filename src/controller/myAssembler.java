package model;

import java.lang.reflect.Array;
import java.math.BigInteger;

/**
 * This class was designed to convert given
 * assembly code to translate over to hexcode
 * (DO NOT USE 'letter', only use hex number
 * to display code!
 * 
 */
public class myAssembler {

	public String userText;
	public String hexCode = "";
	public String binaryCode;
	public String [] words;
	Converter convert;
	public myAssembler(String Text){
		userText = Text;
		convert = new Converter();
	}
	
	public void assembleSourceCodeToObjectCode() {
		userText = userText.replaceAll("\n"," ");
		userText = userText.replaceAll("\t", "");
		userText = userText.replaceAll("  ", "");
		userText = userText.replaceAll(","," ");
		//System.out.println(userText);
		words = userText.split("\\s+");
		translateToBin(words);
		translateToHex(words);
		
		hexCode = hexCode.replaceAll("i"," ");
		hexCode = hexCode.replaceAll("d"," ");
		hexCode = hexCode.replaceAll("n"," ");
		hexCode = hexCode.replaceAll("x"," ");
		hexCode = hexCode.replaceAll("s"," ");
		hexCode = hexCode.replaceAll("sx"," ");
		hexCode = hexCode.replaceAll("sxf"," ");
		
		//System.out.println(hexCode);
		//for(int i = 0; i < words.length; i++) {
		//	System.out.println(words[i]);
		//}
	}

	private void translateToHex(String[] words) {
		convert = new Converter();
		for(int i = 0; i < words.length; i++) {
	       if( words[i].length() == 8) {
	    	   if (convert.binToHex(words[i]).length() == 1) {
	    		   words[i] = "0"+ convert.binToHex(words[i]) + " ";
	    	   }
	    	   else {
	    	       words[i] = convert.binToHex(words[i]) + " ";
	    	   }
	       }
	       
	       hexCode += words[i]; 
		}
		
	}
    
	public String getHexCode() {
		return hexCode;
	}
	
	private void translateToBin(String[] words) {
		//binaryCode = new String[words.length];
		for(int i = 0; i < words.length; i++ ) {
			//Convert words and hex number
			if(words[i].length() > 1 && words[i].substring(0, 2).equals("0x")) {
				words[i] = "00 " + (String) words[i].subSequence(4, words[i].length());
			}
			
			switch(words[i]) {
			case ".END"  : words[i] = ".zz";      break;
			case "STOP"  : words[i] = "00000000"; break;
			
			case "RETTR" : words[i] = "00000001"; break;
			
			case "BR"    :                   //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00000100";
					break;
				}
				else {
					words[i] = "00000101";
					break;
				}
				
			case "BRLE"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00000110";
					break;
				}
				else {
					words[i] = "00000111";
					break;
				}
				
				
			case "BRLT"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00001000";
					break;
				}
				else {
					words[i] = "00001001";
					break;
				}

			case "BREQ"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00001010";
					break;
				}
				else {
					words[i] = "00001010";
					break;
				}
				
			case "BRNE"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00001100";
					break;
				}
				else {
					words[i] = "00001101";
					break;
				}
				
			case "BRGE"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00001110";
					break;
				}
				else {
					words[i] = "00001111";
					break;
				}	
				
			case "BRGT"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00010000";
					break;
				}
				else {
					words[i] = "00010001";
					break;
				}
				
			case "BRV"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00010010";
					break;
				}
				else {
					words[i] = "00010011";
					break;
				}
			   
				
			case "BRC"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00010100";
					break;
					
				}
				else {
					words[i] = "00010101";
					break;
				}	
				
			case "CALL"    :                 //If i then 0, if x then 1
				if(words[i+2].equals("i")) {
					words[i] = "00010110";
					break;
				}
				else {
					words[i] = "00010111";
					break;
				}
				
			case "NOTA"     : words[i] = "00011001"; break;//if A then 1
			case "NOTX"     : words[i] = "00011000"; break;//if X then 0
			
			case "NEGA"     : words[i] = "00011011"; break;//if A then 1
			case "NEGX"     : words[i] = "00011010"; break;//if X then 0
			
			case "ASLA"     : words[i] = "00011101"; break;//if A then 1
			case "ASLX"     : words[i] = "00011100"; break;//if X then 0
			
			case "ASRA"     : words[i] = "00011111"; break;//if A then 1
			case "ASRX"     : words[i] = "00011110"; break;//if X then 0
			
			case "ROLA"     : words[i] = "00100001"; break;//if A then 1
			case "ROLX"     : words[i] = "00100000"; break;//if X then 0
			
			case "RORA"     : words[i] = "00100011"; break;//if A then 1
			case "RORX"     : words[i] = "00100010"; break;//if X then 0
			
			case "CHARO" :
				//System.out.println(words[i]);
				//System.out.println(words[i + 2]);
				if(words[i+ 2].equals("i")) {
					//System.out.println("True");
					words[i] = "11000000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11000001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11000010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11000011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11000100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11000101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11000110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11000111"; break;
				}
				break;
				
			case "CHARI" :
				if(words[i+ 2].equals("i")) {				
					words[i] = "01001000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "01001001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "01001010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "01001011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "01001100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "01001101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "01001110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "01001111"; break;
				}
				break;
				
			case "ADDA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "01111000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "01111001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "01111010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "01111011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "01111100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "01111101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "01111110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "01111111"; break;
				}
				break;
				
			case "ADDX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "01110000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "01110001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "01110010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "01110011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "01110100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "01110101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "01110110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "01110111"; break;
				}
				break;
				
			case "SUBA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "10001000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10001001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10001010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10001011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10001100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10001101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10001110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "01111111"; break;
				}
				break;
				
			case "SUBX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "10000000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10000001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10000010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10000011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10000100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10000101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10000110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "10000111"; break;
				}
				break;
				
			case "ANDA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "10011000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10011001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10011010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10011011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10011100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10011101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10011110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "10011111"; break;
				}
				break;
				
			case "ANDX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "10010000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10010001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10010010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10010011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10010100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10010101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10010110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "10010111"; break;
				}
				break;
				
			case "ORA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "10101000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10101001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10101010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10101011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10101100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10101101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10101110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "10101111"; break;
				}
				break;
				
			case "ORX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "10100000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10100001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10100010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10100011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10100100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10100101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10100110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "10100111"; break;
				}
				break;
				
			case "CPA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "10111000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10111001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10111010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10111011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10111100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10111101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10111110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "10111111"; break;
				}
				break;
				
			case "CPX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "10110000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "10110001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "10110010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "10110011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "10110100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "10110101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "10110110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "10110111"; break;
				}
				break;
				
			case "LDA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "11001000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11001001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11001010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11001011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11001100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11001101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11001110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11001111"; break;
				}
				break;
				
			case "LDX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "11000000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11000001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11000010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11000011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11000100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11000101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11000110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11000111"; break;
				}
				break;
				
			case "LDBYTEA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "11011000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11011001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11011010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11011011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11011100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11011101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11011110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11011111"; break;
				}
				break;
				
			case "LDBYTEX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "11010000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11010001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11010010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11010011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11010100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11010101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11010110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11010111"; break;
				}
				break;
				
			case "STA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "11101000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11101001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11101010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11101011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11101100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11101101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11101110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11101111"; break;
				}
				break;
				
			case "STX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "11100000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11100001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11100010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11100011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11100100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11100101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11100110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11100111"; break;
				}
				break;
				
			case "STBYTEA" :                         //if A then 1
				if(words[i+ 2].equals("i")) {				
					words[i] = "11111000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11111001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11111010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11111011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11111100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11111101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11111110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11111111"; break;
				}
				break;
				
			case "STBYTEX" :                         //if X then 0
				if(words[i+ 2].equals("i")) {				
					words[i] = "11100000"; break;
				}
				else if(words[i+ 2].equals("d")) {
					words[i] = "11100001"; break;
				}
				else if(words[i+ 2].equals("n")) {
					words[i] = "11100010"; break;
				}
				else if(words[i+ 2].equals("s")) {
					words[i] = "11100011"; break;
				}
				else if(words[i+ 2].equals("sf")) {
					words[i] = "11100100"; break;
				}
				else if(words[i+ 2].equals("x")) {
					words[i] = "11100101"; break;
				}
				else if(words[i+ 2].equals("sx")) {
					words[i] = "11100110"; break;
				}
				else if(words[i+ 2].equals("sxf")) {
					words[i] = "11100111"; break;
				}
				break;
		  }
		}
	}


}
