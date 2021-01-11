package the_bit;

public class ALU_test {

	private Longword a = new Longword();
	private Longword b = new Longword();
	private Longword expected = new Longword();

	private Bit[] operation = new Bit[4]; 

	private Longword_test test = new Longword_test(); // for using Longword class test methods

	// counters for pass/fail percentage
	private double pass = 0;
	private double counter = 0;

	// prints percentage of pass
	public void percentage() {
		System.out.println(pass/counter * 100 + "% passed");
	}

	public void runTests() {

		// instantiate
		for (int i=0; i<4; i++){
			operation[i] = new Bit();
		}

		// 8 and 1000
		System.out.println("***** and *****");
		{
			operation[0].set(1);
			operation[1].set(0);
			operation[2].set(0);
			operation[3].set(0); 

			// a = 10 = 1111
			// b = 15 = 1010
			// expected = 1010
			a.set(15);
			b.set(10);
			expected.set(10);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 55 = 110111
			// b = 15 = 101100
			// expected = 100100
			a.set(15);
			b.set(10);
			expected.set(10);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 41329 = 1010000101110001
			// b = 20093 = 0100111001111101
			// expected    = 0000000001110001
			a.set(15);
			b.set(10);
			expected.set(10);
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}

		// 9 or 1001
		System.out.println("***** or *****");
		{
			operation[0].set(1);
			operation[1].set(0);
			operation[2].set(0);
			operation[3].set(1); 

			// a = 15 = 1111
			// b = 10 = 1010
			// expected = 1111
			a.set(15);
			b.set(10);
			expected.set(15);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 55 = 110111
			// b = 15 = 101100
			// expected = 111111
			a.set(55);
			b.set(15);
			expected.set(63);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 41329 = 1010000101110001
			// b = 20093 = 0100111001111101
			// expected    = 1110111101111101
			a.set(41329);
			b.set(20093);
			expected.set(61309);
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}

		// 10 xor 1010
		System.out.println("***** xor *****");
		{
			operation[0].set(1);
			operation[1].set(0);
			operation[2].set(1);
			operation[3].set(0); 

			// a = 10 = 1111
			// b = 15 = 1010
			// expected = 0101
			a.set(15);
			b.set(10);
			expected.set(5);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 55 = 110111
			// b = 15 = 101100
			// expected = 011011
			a.set(55);
			b.set(44);
			expected.set(27);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 41329 = 1010000101110001
			// b = 20093 = 0100111001111101
			// expected    = 1110111100001100
			a.set(41329);
			b.set(20093);
			expected.set(61196);
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}

		// 11 not 1011
		System.out.println("***** not *****");
		{
			operation[0].set(1);
			operation[1].set(0);
			operation[2].set(1);
			operation[3].set(1); 

			// test bits
			Bit one = new Bit();
			Bit zero = new Bit();
			one.set(1);
			zero.set(0);

			// a = 0
			// expected = 1111 1111 1111 1111 1111 1111 1111 1111
			a.set(0);
			for (int i=0; i<32; i++) {
				expected.setBit(i, one);
			}
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 1111 1111 1111 1111 1111 1111 1111 1111
			// expected = 0000 0000 0000 0000 0000 0000 0000 0000
			for (int i=0; i<32; i++) {
				a.setBit(i, one);
			}
			expected.set(0);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a = 1111 1111 1111 1111 1111 1111 1111 0000
			// expected = 0000 0000 0000 0000 0000 0000 0000 1111
			for (int i=28; i<32; i++) {
				a.setBit(i, zero);
			}
			expected.set(15); 
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}

		// 12 leftshift 1100
		System.out.println("***** leftshift *****");
		{
			operation[0].set(1);
			operation[1].set(1);
			operation[2].set(0);
			operation[3].set(0); 

			// a =        0000 0000 0000 0000 0000 0000 0000 1001
			// expected = 0000 0000 0000 0000 0000 0010 0100 0000
			a.set(9);
			b.set(6);
			expected.set(576);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a =        0000 0000 0000 0000 0000 0011 1000 0000
			// expected = 0000 0000 0000 0000 0001 1100 0000 0000
			a.set(896);
			b.set(3);
			expected.set(7168);
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}

		// 13 rightshift 1101
		System.out.println("***** rightshift *****");
		{
			operation[0].set(1);
			operation[1].set(1);
			operation[2].set(0);
			operation[3].set(1); 
			// a =        0000 0000 0000 0000 0000 0000 1111 0000
			// expected = 0000 0000 0000 0000 0000 0000 0000 1111
			a.set(240);
			b.set(4);
			expected.set(15);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// a =        0000 0000 0000 0000 0000 0011 1000 0000
			// expected = 0000 0000 0000 0000 0000 0000 0000 1110
			a.set(896);
			b.set(6);
			expected.set(14);
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}

		// 14 add 1110
		System.out.println("***** add *****");
		{
			operation[0].set(1);
			operation[1].set(1);
			operation[2].set(1);
			operation[3].set(0); 

			// 1010 + 0111 = 10011
			a.set(10);
			b.set(9);
			expected.set(19);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// 1101010001111001 + 111001010011110 = 10100011100010111
			a.set(54393);
			b.set(29342);
			expected.set(83735);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// 11000000111001 + 1101010000110001 = 10100010110000101010
			a.set(12345);
			b.set(54321);
			expected.set(66666);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// positive + negative
			// 101 + 11111111111111111111111111111010 = 11111111111111111111111111111111
			a.set(5);
			b.set(-6);
			expected.set(-1);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// negative + positive
			// 11111111111111111111111111000100 + 110010 = 11111111111111111111111111110110
			a.set(-60);
			b.set(50);
			expected.set(-10);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// negative + negative
			// 11111111111111111111111111000100 + 11111111111111111111111111000100 = 11111111111111111111111110001000
			a.set(-60);
			b.set(-60);
			expected.set(-120);
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}

		// 15 subtract 1111
		System.out.println("***** subtract *****");
		{
			operation[0].set(1);
			operation[1].set(1);
			operation[2].set(1);
			operation[3].set(1); 

			// 1010 - 0100 = 0110
			a.set(10);
			b.set(4);
			expected.set(6);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// 1101010001111001 - 111001010011110 = 110000111011011
			a.set(54393);
			b.set(29342);
			expected.set(25051);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// 100110001001011001111111 - 100110001001011001111111 = 0
			a.set(9999999);
			b.set(9999999);
			expected.set(0);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// positive - negative
			// 101 - 1111111111111010 = 1011
			a.set(5);
			b.set(-6);
			expected.set(11);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// negative - positive
			// 110010 - 11111111111111111111111111000100 = 11111111111111111111111110010010
			a.set(-60);
			b.set(50);
			expected.set(-110);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// negative - negative
			// 1111111111111010 - 1111111111111010 = 0
			a.set(-6);
			b.set(-6);
			expected.set(0);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

		}

		// 7 multiply 0111
		System.out.println("***** multiply *****");
		{
			operation[0].set(0);
			operation[1].set(1);
			operation[2].set(1);
			operation[3].set(1); 

			// 1 * 12 = 12
			a.set(1);
			b.set(12);
			expected.set(12);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// works other way around
			// 12 * 1 = 12 
			a.set(12);
			b.set(1);
			expected.set(12);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// bigger number
			// 27634 * 9856 = 272360704
			a.set(27634);
			b.set(9856);
			expected.set(272360704);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// negative * positive
			// -24 * 32 = -768
			a.set(-24);
			b.set(32);
			expected.set(-768);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// positive * negative
			// 32 * -24 = -768
			a.set(32);
			b.set(-24);
			expected.set(-768);
			test.compareLongword(ALU.doOp(operation, a, b), expected);

			// negative * negative
			// -24 * -24 = -768
			a.set(-24);
			b.set(-24);
			expected.set(576);
			test.compareLongword(ALU.doOp(operation, a, b), expected);
		}
		
		// invalid operation input
		operation[0].set(0);
		operation[1].set(0);
		operation[2].set(0);
		operation[3].set(0);
		a.set(-24);
		b.set(-24);
		// will print "Invalid input"
		ALU.doOp(operation, a, b);
		
		test.percentage();
	}
}
