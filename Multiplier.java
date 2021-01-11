package the_bit;

public class Multiplier {

	// multiplies a and b and returns a new Longword using the bit operations to simulate a multiplication
	public static Longword multiply (Longword a, Longword b) {

		Longword result = new Longword();
		Longword addend = new Longword();
		
		int counter = 0; // counter for shifting
		
		result.set(0); // initialize
		
		// multiplication step
		for (int i=31; i>0; i--) {
			if (b.getBit(i).getValue() == 1) {
				addend = a.leftShift(counter);
				result = RippleAdder.add(result, addend);
			}
			counter++;
		}

		return result;
	}

}
