/* 
 Created by: Hyungsuk Kim
 Created on: 8/24/2020
 email - hkim29@albany.edu 
 */

package the_bit;

public class Bit implements IBit {

	private int oneBit; // a variable that represents a single bit

	// constructor 
	// initializes oneBit to 0
	public Bit() {
		
		this.oneBit = 0;
	}
	
	// sets the value of the bit
	public void set(int value) {
			try {
				// if invalid, throws exception
				if(value<0 || value>1) {
					throw new InvalidInputException();
				} 
			} catch (InvalidInputException e) {
				System.out.println("Invalid Bit input");			
			}
		this.oneBit = value;
	}

	// changes the value from 0 to 1 or 1 to 0
	public void toggle() {

		if(this.oneBit == 0) {
			this.oneBit = 1;
		}
		else this.oneBit = 0;
	}

	// sets the bit to 1
	public void set() {

		this.oneBit = 1;
	}

	// sets the bit to 0
	public void clear() {

		this.oneBit = 0;
	}

	// returns the current value
	public int getValue() {

		return oneBit;
	}

	// performs and on two bits and returns a new bit set to the result
	public Bit and(Bit other) {		

		Bit newBit = new Bit(); // new bit for return
		if(oneBit == 1) {
			if(other.oneBit == 1) {
				newBit.set();
				return newBit;
			}
		}
		newBit.clear();
		return newBit;
	}

	// performs or on two bits and returns a new bit set to the result
	public Bit or(Bit other) {

		Bit newBit = new Bit(); // new bit for return
		if(oneBit == 0) {
			if(other.oneBit == 0) {
				newBit.clear();
				return newBit;
			}
		}
		newBit.set();
		return newBit;
	}

	// performs xor on two bits and returns a new bit set to the result
	public Bit xor(Bit other) {

		Bit newBit = new Bit(); // new bit for return
		if(oneBit != other.oneBit) {
			newBit.set();
			return newBit;
		}
		newBit.clear();
		return newBit;
	}

	// performs not on the existing bit, returning the result as a new bit
	public Bit not() {

		Bit newBit = new Bit(); // new bit for return
		newBit.oneBit = this.oneBit;
		newBit.toggle();
		return newBit;
	}

	// returns ¡°0¡± or ¡°1¡±
	@Override
	public String toString() {
		String string = "" + this.oneBit;
		return string;

	}
}
