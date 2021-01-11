package the_bit;

public class Longword_test {

	// 3 longwords for test runs
	private Longword longword1 = new Longword();
	private Longword longword2 = new Longword();
	private Longword longwordExpected = new Longword();


	// 2 bits for test runs
	private Bit bitOne = new Bit(); // always 1
	private Bit bitZero = new Bit(); // always 0
	private Bit bitExpected = new Bit();

	// counters for pass/fail percentage
	private double pass = 0;
	private double counter = 0;

	// prints percentage of pass
	public void percentage() {
		System.out.println(pass/counter * 100 + "% passed");
	}
	
	// compares actual value and expected value of int and prints the result
	// increments pass, counter upon successful condition
	public void compareInt(int actual, int expected) {

		if (actual == expected) {
			System.out.println("actual value: " + actual);
			System.out.println("expected value: " + expected);
			System.out.println("Pass\n");	
			pass++;
		}
		else {
			System.out.println("actual value: " + actual);
			System.out.println("expected value: " + expected);
			System.out.println("Fail\n");
		}
		counter ++;
	}
	// compares actual value and expected value of Bit and prints the result
	// increments pass, counter upon successful condition
	public void compareBit(Bit bit, Bit expected) {

		if (bit.getValue() == expected.getValue()) {
			System.out.println("actual value    : " + bit.getValue());
			System.out.println("expected value  : " + expected.getValue());
			System.out.println("Pass\n");	
			pass++;
		}
		else {
			System.out.println("actual value    : " + bit.getValue());
			System.out.println("expected value  : " + expected.getValue());
			System.out.println("Fail\n");
		}
		counter ++;
	}
	// compares actual value and expected value of Longword and prints the result
	// increments pass, counter upon successful condition
	public void compareLongword(Longword longword, Longword expected) {
		boolean equal = true;
		for (int i=0; i<32; i++) {
			if(longword.getBit(i).getValue() != expected.getBit(i).getValue()) {
				equal = false;
				break;
			}
		}
		if (equal) {
			System.out.println("actual value    : " + longword.toString());
			System.out.println("expected value  : " + expected.toString());
			System.out.println("Pass\n");	
			pass++;
		}
		else {
			System.out.println("actual value    : " + longword.toString());
			System.out.println("expected value  : " + expected.toString());
			System.out.println("Fail\n");
		}
		counter ++;
	}

	public void runTests() {

		bitOne.set();
		bitZero.clear();

		// have to test together since I need to use setBit to initialize, and use getBit to check for answers.
		System.out.println("Bit getBit(int i) + void setBit(int i, Bit value)");	
		{
			// all 0
			for (int i=0; i<32; i++) {
				longword1.setBit(i, bitZero);
			}
			bitExpected.clear(); // expected Bit = 0
			compareBit(longword1.getBit(0), bitExpected);
			compareBit(longword1.getBit(15), bitExpected);
			compareBit(longword1.getBit(31), bitExpected);

			// all 1
			for (int i=0; i<32; i++) {
				longword1.setBit(i, bitOne);
			}
			bitExpected.set(); // expected Bit = 1
			compareBit(longword1.getBit(0), bitExpected);
			compareBit(longword1.getBit(15), bitExpected);
			compareBit(longword1.getBit(31), bitExpected);
		}

		System.out.println("longword and(longword other)");
		{
			// longword1 = 10 = 1111
			// longword2 = 15 = 1010
			// expected value = 1010
			longword1.set(15);
			longword2.set(10);
			longwordExpected.set(10);
			compareLongword(longword1.and(longword2), longwordExpected);

			// longword1 = 55 = 110111
			// longword2 = 15 = 101100
			// expected value = 100100
			longword1.set(55);
			longword2.set(44);
			longwordExpected.set(36);
			compareLongword(longword1.and(longword2), longwordExpected);

			// longword1 = 41329 = 1010000101110001
			// longword2 = 20093 = 0100111001111101
			// expected value    = 0000000001110001
			longword1.set(41329);
			longword2.set(20093);
			longwordExpected.set(113);
			compareLongword(longword1.and(longword2), longwordExpected);
		}

		System.out.println("longword or(longword other)");
		{
			// longword1 = 10 = 1111
			// longword2 = 15 = 1010
			// expected value = 1111
			longword1.set(15);
			longword2.set(10);
			longwordExpected.set(15);
			compareLongword(longword1.or(longword2), longwordExpected);

			// longword1 = 55 = 110111
			// longword2 = 15 = 101100
			// expected value = 111111
			longword1.set(55);
			longword2.set(44);
			longwordExpected.set(63);
			compareLongword(longword1.or(longword2), longwordExpected);

			// longword1 = 41329 = 1010000101110001
			// longword2 = 20093 = 0100111001111101
			// expected value    = 1110111101111101
			longword1.set(41329);
			longword2.set(20093);
			longwordExpected.set(61309);
			compareLongword(longword1.or(longword2), longwordExpected);
		}

		System.out.println("longword xor(longword other)");
		{
			// longword1 = 10 = 1111
			// longword2 = 15 = 1010
			// expected value = 0101
			longword1.set(15);
			longword2.set(10);
			longwordExpected.set(5);
			compareLongword(longword1.xor(longword2), longwordExpected);

			// longword1 = 55 = 110111
			// longword2 = 15 = 101100
			// expected value = 011011
			longword1.set(55);
			longword2.set(44);
			longwordExpected.set(27);
			compareLongword(longword1.xor(longword2), longwordExpected);

			// longword1 = 41329 = 1010000101110001
			// longword2 = 20093 = 0100111001111101
			// expected value    = 1110111100001100
			longword1.set(41329);
			longword2.set(20093);
			longwordExpected.set(61196);
			compareLongword(longword1.xor(longword2), longwordExpected);
		}

		System.out.println("longword not()");
		{
			// longword1      = 0
			// expected value = 1111 1111 1111 1111 1111 1111 1111 1111
			longword1.set(0);
			for (int i=0; i<32; i++) {
				longwordExpected.setBit(i, bitOne);
			}
			compareLongword(longword1.not(), longwordExpected);

			// longword1      = 1111 1111 1111 1111 1111 1111 1111 1111
			// expected value = 0000 0000 0000 0000 0000 0000 0000 0000
			for (int i=0; i<32; i++) {
				longword1.setBit(i, bitOne);
			}
			longwordExpected.set(0);
			compareLongword(longword1.not(), longwordExpected);

			// longword1      = 1111 1111 1111 1111 1111 1111 1111 0000
			// expected value = 0000 0000 0000 0000 0000 0000 0000 1111
			for (int i=28; i<32; i++) {
				longword1.setBit(i, bitZero);
			}
			longwordExpected.set(15);
			compareLongword(longword1.not(), longwordExpected);
		}

		System.out.println("longword rightShift(int amount)");
		{
			// longword1      = 0000 0000 0000 0000 0000 0000 1111 0000
			// expected value = 0000 0000 0000 0000 0000 0000 0000 1111
			longword1.set(240);
			longwordExpected.set(15);
			compareLongword(longword1.rightShift(4), longwordExpected);

			// longword1      = 0000 0000 0000 0000 0000 0011 1000 0000
			// expected value = 0000 0000 0000 0000 0000 0000 0000 1110
			longword1.set(896);
			longwordExpected.set(14);
			compareLongword(longword1.rightShift(6), longwordExpected);
		}


		System.out.println("longword leftShift(int amount)");
		{
			// longword1      = 0000 0000 0000 0000 0000 0000 0000 1001
			// expected value = 0000 0000 0000 0000 0000 0010 0100 0000
			longword1.set(9);
			longwordExpected.set(576);
			compareLongword(longword1.leftShift(6), longwordExpected);

			// longword1      = 0000 0000 0000 0000 0000 0011 1000 0000
			// expected value = 0000 0000 0000 0000 0001 1100 0000 0000
			longword1.set(896);
			longwordExpected.set(7168);
			compareLongword(longword1.leftShift(3), longwordExpected);
		}



		System.out.println("String toString()");
		{

			// longword1      = 0000 0000 0000 0000 0000 0000 0000 1010
			// expected value = 0000 0000 0000 0000 0000 0000 0000 1010
			longword1.set(10);
			longwordExpected.set(10);
			if (longword1.toString().equals(longwordExpected.toString())) {
				System.out.println("actual value    : " + longword1.toString());
				System.out.println("expected value  : " + longwordExpected.toString());
				System.out.println("pass\n");
				pass++;
			}
			else {
				System.out.println("actual value    : " + longword1.toString());
				System.out.println("expected value  : " + longwordExpected.toString());
				System.out.println("fail\n");
			}
			counter++;

			// longword1      = 0000 0000 0000 0000 0000 0000 0010 0001
			// expected value = 0000 0000 0000 0000 0000 0000 0010 0001
			longword1.set(33);
			longwordExpected.set(33);
			if (longword1.toString().equals(longwordExpected.toString())) {
				System.out.println("actual value    : " + longword1.toString());
				System.out.println("expected value  : " + longwordExpected.toString());
				System.out.println("pass\n");
				pass++;
			}
			else {
				System.out.println("actual value    : " + longword1.toString());
				System.out.println("expected value  : " + longwordExpected.toString());
				System.out.println("fail\n");
			}
			counter++;

		}

		System.out.println("long getUnsigned()");
		{
			// longword1      = 4294967295
			// expected value = 4294967295
			// bit representation = 1111 1111 1111 1111 1111 1111 1111 1111
			for (int i=0; i<32; i++) {
				longword1.setBit(i, bitOne);
			}
		}


		System.out.println("int getSigned()");
		{
			// longword1      = -1
			// expected value = -1
			// bit representation = 1111 1111 1111 1111 1111 1111 1111 1111
			for (int i=0; i<32; i++) {
				longword1.setBit(i, bitOne);
			}
			compareInt(longword1.getSigned(), -1);

			// longword1      = 2147483647
			// expected value = 2147483647
			// bit representation = 0111 1111 1111 1111 1111 1111 1111 1111
			longword1.setBit(0, bitZero);
			compareInt(longword1.getSigned(), 2147483647);
		}


		System.out.println("void copy(longword other)");
		{
			// longword1      = 0000 0000 0000 0000 0000 0000 1010 0101
			// expected value = 0000 0000 0000 0000 0000 0000 1010 0101
			longwordExpected.set(165);
			longword1.copy(longwordExpected);
			compareLongword(longword1, longwordExpected);

			// longword1      = 0000 0000 0000 0001 0010 0000 1010 1001
			// expected value = 0000 0000 0000 0001 0010 0000 1010 1001
			longwordExpected.set(73897);
			longword1.copy(longwordExpected);
			compareLongword(longword1, longwordExpected);
		}


		System.out.println("void set(int value)");
		{
			// longword1      = 15 = 1111
			// expected value = 15 = 1111
			longword1.set(15);
			longwordExpected.set(15);
			compareLongword(longword1, longwordExpected);

			// longword1      = 55 = 11111
			// expected value = 55 = 110111
			longword1.set(55);
			longwordExpected.set(55);
			compareLongword(longword1, longwordExpected);

			// longword1      = 41329 = 1010000101110001
			// expected value = 41329 = 1010000101110001
			longword1.set(41329);
			longwordExpected.set(41329);
			compareLongword(longword1, longwordExpected);
		}

		percentage();

	}
}
