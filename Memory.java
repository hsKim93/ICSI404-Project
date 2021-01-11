package the_bit;

public class Memory {
	
	private Bit[] mem = new Bit[8192]; // memory consisting of 1024 bytes = 8192 bits
	
	// constructor
	// instantiates Bit[]
	public Memory() {
		
		for (int i=0; i<mem.length; i++) {
			mem[i] = new Bit();
		}
	}
	
	// returns 4 bytes from "address" byte
	public Longword read(Longword address) {
		
		Longword result = new Longword();
		
		int index = address.getSigned();
		
		try {
			// if invalid, throws exception
			if(index<0 || index>1021) {
				throw new InvalidInputException("Invalid address");
			} 
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());			
		}
		
		index = index * 8;
		
		for (int i=0; i<32; i++) {	
			result.setBit(i, mem[index + i]);
		}
		return result;
	}
	
	// stores 4 bytes, "value," at "address" byte
	public void write(Longword address, Longword value) {
		
		int index = address.getSigned();
		
		try {
			// if invalid, throws exception
			if(index<0 || index>1021) {
				throw new InvalidInputException("Invalid address");
			} 
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());			
		}
		
		index = index * 8;
	
		for (int i=0; i<32; i++) {
			this.mem[index + i].set(value.getBit(i).getValue());
		}
	}
	
	// toString for memory
	public String toString() {

		String result = "INSTRUCTION MEMORY\n";
//		for testing
		for (int i=0; i<320; i++) {
//		for (int i=0; i<8192; i++) {
			if(i!= 0 && i%32 == 0) {
				result += "\n";
			}
			if(i%16 == 0 && i%32 != 0) {
				result += " | ";
			}
			result += mem[i].getValue();
		}
		// modified for CPU_test3
		result += "\n\nSTACK MEMORY";
		for (int i=8192-160; i<8192; i++) {
			if(i!= 0 && i%32 == 0) {
				result += "\n";
			}
			result += mem[i].getValue();
		}

		
		return result;
	}

}
