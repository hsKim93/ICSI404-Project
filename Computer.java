package the_bit;

public class Computer {

	private Memory memory;
	private Longword PC; // program counter
	private Longword SP; // stack counter
	private Longword op1, op2;
	private int opcode_int, result_int;
	/* branch_type
	 * 00 IfEqual
	 * 01 IfNotEqual
	 * 10 IfGreaterThan
	 * 11 IfGreaterThanOrEqual
	 */
	private int branch_type;
	private boolean do_branch;
	private Longword currentInstruction;
	private Longword[] registers;
	private Longword result, temp;
	private Bit halt; // 0 = running, 1 = halt
	private Bit[] opcode;
	/* comp_result
	 * 00 a=b
	 * 01 a>b
	 * 10 a<b
	 */
	private Bit[] comp_result;
	private Bit[] stack_type;

	// constructor
	public Computer() {

		// initialize
		halt = new Bit();
		opcode = new Bit[4];
		memory = new Memory();
		PC = new Longword();
		SP = new Longword();
		SP.set(1020);
		op1 = new Longword();
		op2 = new Longword();
		result = new Longword();
		temp = new Longword();
		do_branch = false;
		result_int = 0;
		opcode_int = 0;
		branch_type = 0;
		currentInstruction = new Longword();
		for(int i=0; i<opcode.length; i++) {
			opcode[i] = new Bit();
		}
		registers = new Longword[16];
		for (int i=0; i<registers.length; i++) {
			registers[i] = new Longword();
		}
		comp_result = new Bit[2];
		comp_result[0] = new Bit();
		comp_result[1] = new Bit();
		stack_type = new Bit[2];
		stack_type[0] = new Bit();
		stack_type[1] = new Bit();
	}

	// runs 4 methods in a loop until halt == 1
	// runs fetch() / decode() / execute() / store()
	public void run() {

		while (halt.getValue() != 1) {
			fetch();
			decode();
			execute();
			store();
		}
	}

	// reads from memory and fetches instruction bits
	public void fetch() {
		currentInstruction.copy(memory.read(PC));
		// PC increment by 2
		Longword two = new Longword();
		two.set(2);
		PC.copy(RippleAdder.add(PC, two));
	}

	// decodes instruction into 
	// "opcode - a - b - c" depending on format required
	public void decode() {

		// gets opcode and makes a copy as int for comparison
		for (int i=0; i<4; i++) {
			opcode[i].set(currentInstruction.getBit(i).getValue());
		}
		opcode_int = convert_opcode(opcode);
		// check if ALU
		if (checkALU(opcode_int)) {
			// op1 = register a
			temp.copy(currentInstruction.leftShift(4));
			temp.copy(temp.rightShift(28));
			op1.copy(registers[temp.getSigned()]);
			// op2 = register b
			temp.copy(currentInstruction.leftShift(8));
			temp.copy(temp.rightShift(28));
			op2.copy(registers[temp.getSigned()]);
		}
		// check if move
		else if (checkMove(opcode_int)) {
			// op1 = register a
			temp.copy(currentInstruction.leftShift(4));
			op1.copy(temp.rightShift(28));
			// op2 = int bc
			temp.copy(currentInstruction.leftShift(8));
			op2.copy(temp.rightShift(24));
		}
		// check if jump
		else if (checkJump(opcode_int)) {
			// op1 = int abc
			temp.copy(currentInstruction.leftShift(4));
			op1.copy(temp.rightShift(20));
		}
		// check if compare
		else if (checkCompare(opcode_int)) {
			// op1 = register b
			temp.copy(currentInstruction.leftShift(8));
			temp.copy(temp.rightShift(28));
			op1.copy(registers[temp.getSigned()]);
			// op2 = register c
			temp.copy(currentInstruction.leftShift(12));
			temp.copy(temp.rightShift(28));
			op2.copy(registers[temp.getSigned()]);
		}
		// check if branch
		else if (checkBranch(opcode_int)) {
			// op1 = CC
			temp.copy(currentInstruction.leftShift(4));
			temp.copy(temp.rightShift(30));
			op1.copy(temp);
			// op2 = int SA...
			temp.copy(currentInstruction.leftShift(6));
			temp.copy(temp.rightShift(22));
			op2.copy(temp);
		}
		// checks if stack
		else if (checkStack(opcode_int)) {
			// gets the next 2 bits and stores in @stack_type to determine type
			temp.copy(currentInstruction.leftShift(4));
			temp.copy(temp.rightShift(30));
			stack_type[0].set(temp.getBit(30).getValue());
			stack_type[1].set(temp.getBit(31).getValue());
			// push, pop
			// bit 0 is off
			if (stack_type[0].getValue() == 0) {
				temp.copy(currentInstruction.leftShift(12));
				temp.copy(temp.rightShift(28));
				op1.copy(temp); // register to push from / pop to
			}
			// call
			// bit 0 is on and bit 1 is off
			else if (stack_type[1].getValue() == 0) {
				temp.copy(currentInstruction.leftShift(6));
				temp.copy(temp.rightShift(22));
				op1.copy(temp); // address to jump
				op2.copy(PC); // address to push
			}
			// return
//			else {
//				Do nothing
//			}
		}
	}

	// executes using ALU and information from decode
	public void execute() {

		// check halt
		if (checkHalt(opcode_int)){
			this.halt.set(1);
		}
		// check move
		else if (checkMove(opcode_int)) {
			result.set(getInt(op2, 8));
		}
		// check interrupt
		else if (checkInterrupt(currentInstruction.rightShift(16))) {
			// 0010 0000 0000 0000
			if (currentInstruction.rightShift(16).getSigned() == 8192) {
				for (int i=0; i<registers.length; i++) {
					System.out.println("register " + i + ": " + registers[i].toString());
				}
				System.out.println("");
			}
			// 0010 0000 0000 0001
			if (currentInstruction.rightShift(16).getSigned() == 8193) {
				System.out.println(memory.toString());
			}
		}
		// check jump
		else if (checkJump(opcode_int)) {
			result.set(getInt(op1, 12));
		}
		// check ALU
		else if (checkALU(opcode_int)){
			result.copy(ALU.doOp(opcode, op1, op2));
		}
		// check compare
		// a-b and stores the result in comp_result
		else if (checkCompare(opcode_int)) {
			result_int = RippleAdder.subtract(op1, op2).getSigned();
		}
		// check branch
		else if (checkBranch(opcode_int)) {
			branch_type = op1.getSigned();
			result.set(getInt(op2, 10));
			// equal
			if (branch_type == 0) {
				// comp_result => a=b
				if (comp_result[0].getValue() == 0 && comp_result[1].getValue() == 0){
					do_branch = true;
				}
				else {
					do_branch = false;
				}
			}
			// not equal
			else if (branch_type == 1) {
				// comp_result => a>b or a<b
				if (comp_result[0].getValue() == 1 || comp_result[1].getValue() == 1){
					do_branch = true;
				}
				else {
					do_branch = false;
				}
			}
			// greater than
			else if (branch_type == 2) {
				// comp_result => a>b
				if (comp_result[0].getValue() == 0 && comp_result[1].getValue() == 1){
					do_branch = true;
				}
				else {
					do_branch = false;
				}
			}
			// greater than or equal
			else if (branch_type == 3) {
				// comp_result => a>b or a=b
				if (comp_result[0].getValue() == 0) {
					do_branch = true;
				} else {
					do_branch = false;
				}
			}
		}
		// check stack
		else if(checkStack(opcode_int)) {
			// push, pop
			// bit 0 is off
			if (stack_type[0].getValue() == 0) {
				result.copy(op1);
			}
			// call
			// bit 0 is on and bit 1 is off
			else if (stack_type[1].getValue() == 0) {
				result.set(getInt(op1, 10)); // address to jump
			}
			// return
			else {
				// move pointer than read
				Longword four = new Longword();    // 4 byte
				four.set(4);
				SP.copy(RippleAdder.add(SP, four)); // SP = SP + 4
				result.copy(memory.read(SP));
				memory.write(SP, new Longword()); // popped
			}
		}

	}

	// stores the result of ALU operation into register(store)
	public void store() {

		// check ALU
		if(checkALU(opcode_int)) {
			temp.copy(currentInstruction.leftShift(12));
			temp.copy(temp.rightShift(28));
			registers[temp.getSigned()].copy(result);
		}
		// check move move
		else if (checkMove(opcode_int)) {
			registers[op1.getSigned()].copy(result);
		}
		// check jump
		else if (checkJump(opcode_int)){
			PC.copy(result);
		}
		// check compare
		else if (checkCompare(opcode_int)) {
			// equal a=b 00
			if (result_int == 0) {
				comp_result[0].set(0);
				comp_result[1].set(0);
			}
			// a is greater a>b 01
			else if (result_int > 0) {
				comp_result[0].set(0);
				comp_result[1].set(1);
			}
			// b is greater a<b 10
			else {
				comp_result[0].set(1);
				comp_result[1].set(0);
			}
		}
		// check branch
		else if (checkBranch(opcode_int)) {
			if (do_branch) {
				Longword two = new Longword();
				two.set(2);
				PC.copy(RippleAdder.subtract(PC, two)); // reset PC increment from fetch()
				PC.copy(RippleAdder.add(PC, result));
			}
		}
		// check stack
		else if (checkStack(opcode_int)) {
			// push, pop
			// bit 0 is off
			if (stack_type[0].getValue() == 0) {
				Longword four = new Longword();    // 4 byte
				four.set(4);
				// push
				if (stack_type[1].getValue() == 0) {
					memory.write(SP, registers[result.getSigned()]);
					// write than move pointer
					SP = RippleAdder.subtract(SP, four); // SP = SP - 4
				}
				// pop
				else {
					// move pointer then read
					SP = RippleAdder.add(SP, four); // SP = SP + 4
					// pop stack
					registers[result.getSigned()].copy(memory.read(SP));
					memory.write(SP, new Longword());
				}
			}
			// call
			// bit 0 is on and bit 1 is off
			else if (stack_type[1].getValue() == 0) {
				PC.copy(result); // jump to result
				// push
				memory.write(SP, op2); // RN
				// write then move pointer
				Longword four = new Longword(); // 4 byte
				four.set(4);
				SP = RippleAdder.subtract(SP, four);
			}
			// return
			else {
				PC.copy(result); // jump to RN
			}
		}
	}

	//////////////////////////////////// helper methods /////////////////////////////////

	// checks if opcode is halt
	// returns false if not
	public boolean checkHalt(int opcode_int) {

		if (opcode_int == 0){
			return true;
		}
		return false;
	}

	// checks if opcode is move
	// returns false if not
	public boolean checkMove(int opcode_int) {

		if (opcode_int == 1){
			return true;
		}
		return false;
	}

	// checks if instruction is interrupt
	// returns false if not
	public boolean checkInterrupt(Longword a) {
		// 0010 0000 0000 0000    || 0010 0000 0000 0001
		if (a.getSigned() == 8192 || a.getSigned() == 8193) {
			return true;
		}
		return false;
	}

	// checks if opcode is jump
	// returns false if not
	public boolean checkJump(int opcode_int) {

		if (opcode_int == 3){
			return true;
		}
		return false;
	}

	// checks if opcode is compare
	// returns false if not
	public boolean checkCompare(int opcode_int){

		if (opcode_int == 4) {
			return true;
		}
		return false;
	}

	// checks if opcode is branch
	// returns false if not
	public boolean checkBranch(int opcode_int){

		if (opcode_int == 5) {
			return true;
		}
		return false;
	}

	// checks if opcode is stack
	// returns false if not
	public boolean checkStack(int opcode_int) {

		if (opcode_int == 6) {
			 return true;
		}
		return false;
	}


	// checks if opcode is ALU
	// returns false if not
	public boolean checkALU(int opcode_int) {

		if (opcode_int >=7 && opcode_int <= 15) {
			return true;
		}
		return false;
	}

	// takes a longword as a parameter
	// returns the value of longword a as an int only using the last @amount of bits
	public int getInt(Longword a, int amount) {

		int result = 0;
		int power = 0; // exponent
		try {
			// if invalid, throws exception
			if (amount != 8) {
				if (amount != 10) {
					if (amount != 12) {
						throw new InvalidInputException();
					}
				}
			}
		} catch (InvalidInputException e) {
			System.out.println("Invalid getInt format");
		}
			if (a.getBit(32 - amount).getValue() == 0 || amount == 12) {
				for (int i = 31; i > 32 - amount; i--) {
					result += a.getBit(i).getValue() * Math.pow(2, power);
					power++;
				}
			} else {
				result = -1;
				for (int i = 31; i > 32 - amount; i--) {
					if (a.getBit(i).getValue() == 0) {
						result -= 1 * Math.pow(2, power);
						power++;
					}
				}
			}

		return result;
	}

	// converts opcode into int for comparison
	// returns int result
	public int convert_opcode(Bit[] opcode){
		int result =0;
		int power = 0;
		for (int i=3; i>=0; i--) {
			result += opcode[i].getValue() * Math.pow(2, power);
			power ++;
		}
		return result;
	}

	// initialization
	// takes an array of strings as parameter
	// format for strings will be "xxxxxxxxxxxxxxxx" where x = 0 or 1
	public void preload(String[] a) {

		Longword address = new Longword(); // holds the address
		Longword four = new Longword();    // 4 byte
		four.set(4);
		Longword holder = new Longword();  // holds the String in Longword format
		String temp = ""; 				   // temp string to concatenate
		Bit bit = new Bit();               // bit to hold char
		// odd number of instructions
		if (a.length%2 == 1) {
			for (int i=0; i< a.length-1; i++) {
				try {
					// if invalid, throws exception
					if (a[i].length() != 16) {
						throw new InvalidInputException();
					} 
				} catch (InvalidInputException e) {
					System.out.println("Invalid String[] format: " + a[i]);
				}

				temp += a[i];
				// after reading in 32 bits 
				if(i%2 == 1) {
					for (int j=0; j<32; j++) {
						bit.set(temp.charAt(j)-48);
						holder.setBit(j, bit);
					}
					// write then reset values / increment address
					memory.write(address, holder);
					temp = "";
					address = RippleAdder.add(address, four);
				}

			}
			temp = a[a.length-1] + "0000000000000000";
			for (int j=0; j<32; j++) {
				bit.set(temp.charAt(j)-48);
				holder.setBit(j, bit);
			}
			memory.write(address, holder);
		}
		// even number of instructions
		else {
			for (int i=0; i< a.length; i++) {
				try {
					// if invalid, throws exception
					if (a[i].length() != 16) {
						throw new InvalidInputException();
					} 
				} catch (InvalidInputException e) {
					System.out.println("Invalid String[] format with length (" + a[i].length() +
							") in " + a[i]);
				}

				temp += a[i];
				// after reading in 32 bits 
				if(i%2 == 1) {
					for (int j=0; j<32; j++) {
						bit.set(temp.charAt(j)-48);
						holder.setBit(j, bit);
					}
					// write then reset values / increment address
					memory.write(address, holder);
					temp = "";
					address = RippleAdder.add(address, four);
				}

			}
		}

	}
}
