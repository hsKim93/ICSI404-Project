package the_bit;

public class Assembler {

	// reads String representation of instructions and parses into binary representation of instruction
	public static String[] assemble(String[] input) {

		String[] result= new String[input.length];
		String[] token;
		for (int i=0; i<result.length; i++) {
			token = tokenizer(input[i]);
			result[i] = lexer(token);

			// exception handling
			try {
				if(result[i] == null) {
					throw new InvalidInputException();
				} 
			} catch (InvalidInputException e) {
				System.out.println("Invalid instruction for Assembler");			
			}
		}
		return result;
	}

	// breaks down an instruction into tokens
	private static String[] tokenizer(String input) {

		String[] result = input.split(" ");
		return result;
	}

	// parse tokens into lexemes
	private static String lexer(String[] token) {

		String result = "";
		// halt
		if (token[0].equalsIgnoreCase("halt") && token.length == 1) {
			return "0000000000000000";
		}
		// interrupt
		else if (token[0].equalsIgnoreCase("interrupt") && token.length == 2) {
			if (token[1].equals("0")) {
				return "0010000000000000";
			}
			else if (token[1].equals("1")) {
				return "0010000000000001";
			}
			else {
				return null;
			}
		}
		// move
		else if (token[0].equalsIgnoreCase("move") && token.length == 3) {
			result = "0001";
			String temp1 = convertRegister(token[1]);
			String temp2 = convertIntSigned(Integer.parseInt(token[2]), 8);
			if (temp1 != null && temp2 != null) {
				result = result + temp1 + temp2;
				return result;
			}
			else {
				return null;
			}
		}
		// jump
		else if (token[0].equalsIgnoreCase("jump") && token.length == 2) {
			result = "0011";
			String temp = convertIntUnsigned(Integer.parseInt(token[1]),12);
			if (temp != null) {
				result += temp;
				return result;
			}
			else {
				return result;
			}
		}
		// compare
		else if (token[0].equalsIgnoreCase("compare") && token.length == 3){
			result = "0100";
			String temp1 = convertRegister(token[1]);
			String temp2 = convertRegister(token[2]);
			if (temp1 != null && temp2 != null) {
				result = result + "0000" + temp1 + temp2;
				return result;
			}
			else {
				return null;
			}
		}
		// branch
		else if ((result = checkBranch(token[0])) != null && token.length == 2){
			String temp = convertIntSigned(Integer.parseInt(token[1]), 10);
			if (temp != null) {
				result += temp;
				return result;
			}
			else {
				return null;
			}
		}
		// stack
		else if ((result = checkStack(token[0])) != null) {
			if (token.length == 2) {
				// push pop
				if (result.length() == 12) {
					String temp = convertRegister(token[1]);
					result += temp;
					return result;
				}
				// call
				else if (result.length() == 6) {
					String temp = convertIntSigned(Integer.parseInt(token[1]), 10);
					result += temp;
					return result;
				}
				else {
					return null;
				}
			}
			// return
			else if (token.length == 1 && result.length() == 16){
				return result;
			}
			else {
				return null;
			}

		}
		// ALU
		else if ((result = checkALU(token[0])) != null) {
			// not should only have 3 tokens 
			if (result.equals("1011")) {
				if (token.length == 3) {
					String temp;
					for (int i=1; i<3; i++) {
						if ((temp = convertRegister(token[i])) != null) {
							if (i == 1) {
								result += temp;
							}
							else {
								result = result + "0000" + temp;
							}
						}
						else {
							return null;
						}
					}
					return result;
				}
				else {
					return null;
				}
			}
			else {
				String temp;
				for (int i=1; i<4; i++) {
					if ((temp = convertRegister(token[i])) != null) {
						result += temp;
					}
					else {
						return null;
					}
				}
				return result;
			}
		}
		// invalid input
		else {
			return null;
		}
	}

	// checks if token is an ALU instruction and returns binary representation of ALU opcode
	// returns null if invalid input
	private static String checkALU(String token) {

		switch (token.toLowerCase()){
		case "mult":
			return "0111";
		case "and":
			return "1000";
		case "or":
			return "1001";
		case "xor":
			return "1010";
		case "not":
			return "1011";
		case "ls":
			return "1100";
		case "rs":
			return "1101";
		case "add":
			return "1110";
		case "subt":
			return "1111";
		default:
			return null;
		}
	}

	// checks if token is a branch instruction and returns binary representation
	// returns null if invalid input
	private static String checkBranch(String token) {

		switch (token.toLowerCase()){
			// if equal
			case "branchife":
				return "010100";
			// if not equal
			case "branchifne":
				return "010101";
			// if greater than
			case "branchifgt":
				return "010110";
			// if greater than or equal
			case "branchifgtoe":
				return "010111";
			default:
				return null;
		}
	}

	// checks if token is a stack instruction and returns binary representation
	// returns null if invalid input
	private static String checkStack(String token) {

		switch (token.toLowerCase()){
			// push 0110 00
			case "push":
				return "011000000000"; // + R
			// pop 0110 01
			case "pop":
				return "011001000000"; // + R
			// call 0110 10
			case "call":
				return "011010"; // + 10 bit absolute address
			// return 0110 11
			case "return":
				return "0110110000000000";
			default:
				return null;
		}
	}

	// converts register into binary representation
	// returns null if invalid input
	private static String convertRegister(String token) {

		if(token.charAt(0) == 'r' || token.charAt(0) == 'R') {
			switch (token.substring(1)){
			case "0":
				return "0000";
			case "1":
				return "0001";
			case "2":
				return "0010";
			case "3":
				return "0011";
			case "4":
				return "0100";
			case "5":
				return "0101";
			case "6":
				return "0110";
			case "7":
				return "0111";
			case "8":
				return "1000";
			case "9":
				return "1001";
			case "10":
				return "1010";
			case "11":
				return "1011";
			case "12":
				return "1100";
			case "13":
				return "1101";
			case "14":
				return "1110";
			case "15":
				return "1111";
			default:
				return null;
			}
		}
		else {
			return null;
		}
	}

	// returns the binary representation of value as String
	// @leng = number of bits
	// returns null if out of range -> 2 byte integer
	private static String convertIntSigned(int value, int leng) {

		String result = "";

		// boundary check
		if (value < (Math.pow(2, leng-1))*-1 || value > (Math.pow(2, leng-1)-1)) {
			return null;
		}
		// positive
		if (value >= 0) {
			// convert
			for (int i = 0; i<leng-1; i++) {
				result = value%2 + result;
				value /= 2;
			}
			result = 0 + result;
		}
		// negative
		else {
			value = Math.abs(value);
			boolean carry = true; // carry condition for 2's compilment
			int temp;
			// convert
			for (int i = 0; i<leng-1; i++) {
				if (value%2 == 0) {
					temp = 1;
					if(carry) {
						temp = 0;
					}
				}
				else {
					temp = 0;
					if(carry) {
						temp = 1;
						carry = false;
					}
				}
				result = temp + result;
				value /= 2;
			}
			result = 1 + result;
		}
		return result;
	}

	// returns the binary representation of value as String
	// @leng = number of bits
	// returns null if out of range -> 2 byte integer
	private static String convertIntUnsigned(int value, int leng) {

		String result = "";
		// boundary check
		if (value < 0 || value > (Math.pow(2, leng)-1)) {
			return null;
		}

		// convert
		for (int i = 0; i<leng; i++) {
			result = value%2 + result;
			value /= 2;
		}
		return result;
	}

}