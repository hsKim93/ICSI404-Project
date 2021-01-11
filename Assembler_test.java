package the_bit;

public class Assembler_test {
	
	public void runTests() {
		
		Computer computer = new Computer();
		
		String[] preloader = new String[19];
		
		// Test result format :
		// {Register} {Longword bits}

		// R0  111...111
		preloader[0] = "move R0 -1";
		// R1  1
		preloader[1] = "move R1 1";
		// R2  11
		preloader[2] = "move R2 3";
		// R3  101
		preloader[3] = "move R3 5";
		// R4  1010
		preloader[4] = "move R4 10";
		// R5  1111
		preloader[5] = "move R5 15";
		// R6  0
		preloader[6] = "move R6 0";
		// R7  111...111
		preloader[7] = "mult R0 R1 R7";
		// R8  1010
		preloader[8] = "and R4 R5 R8";
		// R9  1111
		preloader[9] = "or R3 R5 R9";
		// R10 1111
		preloader[10] = "xor R3 R4 R10";
		// R11 111...111
		preloader[11] = "not R6 R11";
		// R12 1000
		preloader[12] = "ls R1 R2 R12";
		// R13 101
		preloader[13] = "rs R4 R1 R13";
		// R14 100
		preloader[14] = "add R1 R2 R14";
		// R15 111...111
		preloader[15] = "subt R6 R1 R15";
		// interrupt 0
		preloader[16] = "interrupt 0";
		// interrupt 1
		preloader[17] = "interrupt 1";
		// halt
		preloader[18] = "halt";
		
		preloader = Assembler.assemble(preloader);
		computer.preload(preloader);
		computer.run();
	}
}
