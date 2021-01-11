package the_bit;

public class ALU {
	public static Longword doOp(Bit[] operation, Longword a, Longword b) {

		// convers Bit[] operation to int
		int op_replace = 0; // int representatino of operation
		int power = 0; // exponent
		
			try {
				// if invalid, throws exception
				if(operation.length<0 || operation.length>4) {
					throw new InvalidInputException();
				} 
			} catch (InvalidInputException e) {
				System.out.println("Invalid ALU");
			}

		
		for (int i=3; i>=0; i--) {
			op_replace += operation[i].getValue() * Math.pow(2, power);
			power ++;
		}
		
		// Operation mapping
		// 8 and
		if (op_replace == 8) {
			return a.and(b);
		}
		// 9 or
		else if (op_replace == 9) {
			return  a.or(b);
		}
		// 10 xor
		else if (op_replace == 10) {
			return a.xor(b);
		}
		// 11 not
		else if (op_replace == 11) {
			return a.not();
		}
		// 12 leftshift
		else if (op_replace == 12) {
			int amount = b.getSigned();
			return a.leftShift(amount);
		}
		// 13 rightshift
		else if (op_replace == 13) {
			int amount = b.getSigned();
			return a.rightShift(amount);
		}
		// 14 add
		else if (op_replace == 14) {
			return RippleAdder.add(a, b);
		}
		// 15 subtract
		else if (op_replace == 15) {
			return RippleAdder.subtract(a, b);
		}
		// 7 multiply
		else {
			return Multiplier.multiply(a, b);
		}
	}

}
