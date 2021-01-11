package the_bit;

public class CPU_test1 {

	public void run_tests() {

		// Testing Move, interrupt0, interrupt1, halt
		// 		   preloaderm, run
		// All working
		System.out.println("Test1");
		Computer computer = new Computer();
		String[] preloader = new String[10];
		preloader[0] = "0001000000000000"; // move r0 0
		preloader[1] = "0001000100000001"; // move r1 1
		preloader[2] = "0001001000000010"; // move r2 2
		preloader[3] = "0001001100000011"; // move r3 3
		preloader[4] = "0001010000000100"; // move r4 4
		preloader[5] = "0001010100000101"; // move r5 5
		preloader[6] = "0001011000000110"; // move r6 6
		preloader[7] = "0010000000000000"; // print registers
		preloader[8] = "0010000000000001"; // print memory
		preloader[9] = "0000000000001111"; // halt
		computer.preload(preloader);
		computer.run();

		// test 2 ALU, odd instruction
		// All working
		System.out.println("Test2");
		computer = new Computer();
		preloader = new String[19];
		preloader[0] = "0001000000000111"; // move r0 7
		preloader[1] = "0001000100001001"; // move r1 9
		preloader[2] = "0001001000001111"; // move r2 15
		preloader[3] = "0001001100010000"; // move r3 16
		preloader[4] = "0001010011111111"; // move r4 -1
		preloader[5] = "0001010100000010"; // move r5 2
		preloader[6] = "0001011000010101"; // move r6 21
		//ALU
		// fix 13, 14 -> fix 4 then should be fine
		preloader[7] = "1000000000100111"; // and r0 r2 r7 = 111 
		preloader[8] = "1001000000011000"; // or  r0 r1 r8 = 1111
		preloader[9] = "1010000100101001"; // xor r1 r2 r9 = 110
		preloader[10] = "1011000100001010"; // not r1 r10 = 11...0110  
		preloader[11] = "1100000001011011"; // leftshift r0 r5 r11 = 11100
		preloader[12] = "1101001101011100"; // rightshift r3 r5 r12 = 100 
		preloader[13] = "1110010001011101"; // add r4 r5 r13 = 1
		preloader[14] = "1111010101001110";	// subtract r5 r4 r14 = 11
		preloader[15] = "0111000000011111"; // multiply r0 r1 r15 = 111111
		// Print halt
		preloader[16] = "0010000000000000"; // print registers
		preloader[17] = "0010000000000001"; // print memory
		preloader[18] = "0000000000001111"; // halt
		computer.preload(preloader);
		computer.run();

	}

}
