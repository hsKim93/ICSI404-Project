package the_bit;

public class Longword implements ILongword {

	private Bit[] bitArray = new Bit[32]; // a bit array representation of a longword

	// constructor
	// instantiates Bit[]
	public Longword() {

		for (int i=0; i<bitArray.length; i++) {
			bitArray[i] = new Bit();
		}
	}

	// Get bit i
	public Bit getBit(int i) {

		try {
			// if invalid, throws exception
			if(i<0 || i>31) {
				throw new InvalidInputException();
			} 
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());			
		}
		return this.bitArray[i];
	}

	// set bit i's value
	public void setBit(int i, Bit value) {

		try {
			// if invalid, throws exception
			if(i<0 || i>31) {
				throw new InvalidInputException();
			} 
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());			
		}
		this.bitArray[i].set(value.getValue());
	}

	// and two longwords, returning a third
	public Longword and(Longword other) {

		Longword result = new Longword();
		for (int i=0; i<32; i++) {
			result.bitArray[i] = this.bitArray[i].and(other.bitArray[i]);
		}
		return result;
	}

	// or two longwords, returning a third
	public Longword or(Longword other) {

		Longword result = new Longword();
		for (int i=0; i<32; i++) {
			result.bitArray[i] = this.bitArray[i].or(other.bitArray[i]);
		}
		return result;
	}

	// xor two longwords, returning a third
	public Longword xor(Longword other) {

		Longword result = new Longword();
		for (int i=0; i<32; i++) {
			result.bitArray[i] = this.bitArray[i].xor(other.bitArray[i]);
		}
		return result;
	}

	// negate this longword, creating another
	public Longword not() {

		Longword result = new Longword();
		for (int i=0; i<32; i++) {
			result.bitArray[i] = this.bitArray[i].not();
		}
		return result;
	}

	// rightshift this longword by amount bits, creating a new longword
	public Longword rightShift(int amount) {

		try {
			// if invalid, throws exception
			if(amount<0 || amount>31) {
				throw new InvalidInputException();
			} 
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());			
		}
		int index = 32 - amount;
		Longword result = new Longword();
		// initializes result
		for (int i=0; i<32; i++) {
			result.bitArray[i] = new Bit();
		}		
		for (int i=0; i<index; i++) {
			result.setBit(i + amount, this.getBit(i));
		}
		return result;
	}

	// leftshift this longword by amount bits, creating a new longword
	public Longword leftShift(int amount) {

		try {
			// if invalid, throws exception
			if(amount<0 || amount>31) {
				throw new InvalidInputException();
			} 
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());	
		}
		int index = 32 - amount;
		Longword result = new Longword();
		// initializes result
		for (int i=0; i<32; i++) {
			result.bitArray[i] = new Bit();
		}
		for (int i=0; i<index; i++) {
			result.setBit(i, this.getBit(i + amount));
		}

		return result;
	}

	// returns a comma separated string of 0's and 1's: "0,0,0,0,0 (etcetera)" for example
	@Override
	public String toString() {

		String result ="" + bitArray[0]; 
		for (int i=1; i<32; i++) {
			result += "," + bitArray[i];
		}
		return result;
	}
	// returns the value of this longword as a long
	public long getUnsigned() {

		long result = 0;
		int power = 0; // exponent
		for (int i=31; i>=0; i--) {
			result += bitArray[i].getValue() * Math.pow(2, power);
			power ++;
		}
		return result;
	}

	// returns the value of this longword as an int
	public int getSigned() {

		int result = 0;
		int power = 0; // exponent
		if (bitArray[0].getValue() == 0) {
			for (int i=31; i>0; i--) {
				result += bitArray[i].getValue() * Math.pow(2, power);
				power ++;
			}
		}
		else {
			result = -1;
			for (int i=31; i>0; i--) {
				if(bitArray[i].getValue() == 0) {
					result -= 1 * Math.pow(2, power);
					power ++;
				}
			}
		}
		return result;
	}

	// copies the values of the bits from another longword into this one
	public void copy(Longword other) {

		for (int i =0; i<32; i++) {
			this.bitArray[i].set(other.bitArray[i].getValue());
		}
	}

	// set the value of the bits of this longword (used for tests)
	public void set(int value) {

		// positive
		if (value >= 0) {
			// initializes Bit array to 0
			for (int i=0; i<32; i++) {
				bitArray[i] = new Bit();
				bitArray[i].clear();
			}
			// converts i to 32 bit binary
			for (int i = 31; i>0; i--) {
				if(i >= 1) {
					bitArray[i].set(value%2);
					value /= 2;
				}
				else {
					break;
				}
			}
		}
		// negative
		else {
			value = Math.abs(value);
			boolean carry = true; // carry condition for 2's compilment
			// initializes Bit array to 0
			for (int i=0; i<32; i++) {
				bitArray[i] = new Bit();
				bitArray[i].clear();
			}
			// converts i to 32 bit binary
			for (int i = 31; i>0; i--) {
				if(i >= 1) {
					bitArray[i].set(value%2);
					// flip for 2's compilment
					bitArray[i].toggle();
					if (bitArray[i].getValue() == 1 && carry == true) {
						bitArray[i].set(0);
					}
					else if (bitArray[i].getValue() == 0 && carry == true) {
						bitArray[i].set(1);
						carry = false;
					}
					value /= 2;
				}
				else {
					break;
				}
			}	
			// sign bit
			bitArray[0].set(1);
		}
	}
}
