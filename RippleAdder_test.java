package the_bit;

public class RippleAdder_test {
	// 2 longwords for testing
	private Longword longword1 = new Longword();
	private Longword longword2 = new Longword();

	// expected longword
	private Longword longword_expected = new Longword();

	// to use Longword_test compare methods
	private Longword_test compare = new Longword_test();

	public void runTests() {

		System.out.println("Longword add(Longword a, Longword b)");
		{
			// 1010 + 0111 = 10011
			longword1.set(10);
			longword2.set(9);
			longword_expected.set(19);
			compare.compareLongword(RippleAdder.add(longword1, longword2), longword_expected);

			// 1101010001111001 + 111001010011110 = 10100011100010111
			longword1.set(54393);
			longword2.set(29342);
			longword_expected.set(83735);
			compare.compareLongword(RippleAdder.add(longword1, longword2), longword_expected);

			// 11000000111001 + 1101010000110001 = 10100010110000101010
			longword1.set(12345);
			longword2.set(54321);
			longword_expected.set(66666);
			compare.compareLongword(RippleAdder.add(longword1, longword2), longword_expected);

			// 101 + 1111111111111010 = 11111111111111111111111111111111
			longword1.set(5);
			longword2.set(-6);
			longword_expected.set(-1);
			compare.compareLongword(RippleAdder.add(longword1, longword2), longword_expected);

			// 110010 + 11111111111111111111111111000100 = 11111111111111111111111111110110
			longword1.set(-60);
			longword2.set(50);
			longword_expected.set(-10);
			compare.compareLongword(RippleAdder.add(longword1, longword2), longword_expected);

		}

		System.out.println("Longword subtract(Longword a, Longword b)");
		{
			// 1010 - 0100 = 0110
			longword1.set(10);
			longword2.set(4);
			longword_expected.set(6);
			compare.compareLongword(RippleAdder.subtract(longword1, longword2), longword_expected);

			// 1101010001111001 - 111001010011110 = 110000111011011
			longword1.set(54393);
			longword2.set(29342);
			longword_expected.set(25051);
			compare.compareLongword(RippleAdder.subtract(longword1, longword2), longword_expected);

			// 100110001001011001111111 - 100110001001011001111111 = 0
			longword1.set(9999999);
			longword2.set(9999999);
			longword_expected.set(0);
			compare.compareLongword(RippleAdder.subtract(longword1, longword2), longword_expected);

			// 101 - 1111111111111010 = 1011
			longword1.set(5);
			longword2.set(-6);
			longword_expected.set(11);
			compare.compareLongword(RippleAdder.subtract(longword1, longword2), longword_expected);

			// 110010 - 11111111111111111111111111000100 = 11111111111111111111111110010010
			longword1.set(-60);
			longword2.set(50);
			longword_expected.set(-110);
			compare.compareLongword(RippleAdder.subtract(longword1, longword2), longword_expected);
			
			// 1111111111111010 - 1111111111111010 = 0
			longword1.set(-6);
			longword2.set(-6);
			longword_expected.set(0);
			compare.compareLongword(RippleAdder.subtract(longword1, longword2), longword_expected);
			
		}
		compare.percentage();

	}
}
