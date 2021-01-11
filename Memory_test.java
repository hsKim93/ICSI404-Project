package the_bit;

public class Memory_test {

	// test memory
	private Memory memory = new Memory();

	// test longword
	private Longword value = new Longword();
	private Longword address = new Longword();
	private Longword expected = new Longword();

	// to use Longword_test compare methods
	private Longword_test compare = new Longword_test();

	public void runTests() {

		// write -> read
		// value 	10
		// address 	0
		// expected 10
		value.set(10);
		address.set(0);
		expected.set(10);
		memory.write(address, value);
		compare.compareLongword(memory.read(address), expected);
		
		// write (overwrite) -> read
		// value 	10
		// address 	1
		// expected 10
		value.set(10);
		address.set(1);
		expected.set(10);
		memory.write(address, value);
		compare.compareLongword(memory.read(address), expected);

		// read (overwritten)
		// address  0
		// expected 10 -> 0
		address.set(0);
		expected.set(0);
		compare.compareLongword(memory.read(address), expected);

		// write -> read
		// value 	2430
		// address 	1000
		// expected 2430
		value.set(2430);
		address.set(1000);
		expected.set(2430);
		memory.write(address, value);
		compare.compareLongword(memory.read(address), expected);

		// read
		// address  999
		// expected 9
		address.set(999);
		expected.set(9);
		compare.compareLongword(memory.read(address), expected);

		// read
		// address 1001
		// expected
		address.set(1001);
		expected.set(622080);
		compare.compareLongword(memory.read(address), expected);
		
		compare.percentage();
	}
	
}
