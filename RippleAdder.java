package the_bit;

public class RippleAdder {


	// adds 2 longwords and returns a new longword simulating the ripple carry adder
	public static Longword add(Longword a, Longword b) {

		Longword temp_result = new Longword(); 	// temp result
		Longword final_result = new Longword(); // final result
		Longword carry1 = new Longword();	// carry bit from first half adder
		Longword carry2 = new Longword();	// carry bit in / out
		
		Bit temp = new Bit(); // bit 0 for initial borrow value
		temp.clear();
		

		for (int i=31; i>=0; i--) {
			temp_result.setBit(i, a.getBit(i).xor(b.getBit(i))); // a xor b
			carry1.setBit(i, a.getBit(i).and(b.getBit(i))); // a and b
			if(i+1 <= 31) {
				final_result.setBit(i, temp_result.getBit(i).xor(carry2.getBit(i+1))); // a xor b xor carrry1
				carry2.setBit(i, temp_result.getBit(i).and(carry2.getBit(i+1)));
			}
			else {
				final_result.setBit(i, temp_result.getBit(i).xor(temp)); // a xor b xor carrry1
				carry2.setBit(i, temp_result.getBit(i).and(temp));
			}
			carry2.setBit(i, carry1.getBit(i).or(carry2.getBit(i)));
		}
		return final_result;
	}

	// subtracts Longword b from Longword a and returns a new longword simulating the ripple borrow subtractor
	public static Longword subtract(Longword a, Longword b) {
		
		Longword result = new Longword();	// result
//		Longword borrow1 = new Longword();	// borrow from first half subtractor
//		Longword borrow2 = new Longword();	// borrow in / out
//
//		Bit temp = new Bit(); // bit 0 for initial borrow value
//		temp.clear();
//
//		for (int i=31; i>=0; i--) {
//			result.setBit(i, a.getBit(i).xor(b.getBit(i))); // a xor b
//			borrow1.setBit(i, (a.getBit(i).not()).and(b.getBit(i))); // (not a) and b
//			if(i+1 <= 31) {
//				result.setBit(i, result.getBit(i).xor(borrow2.getBit(i+1))); // a xor b xor borrow2
//				borrow2.setBit(i, (a.getBit(i).xor(b.getBit(i)).not()).and(borrow2.getBit(i+1))); // not borrow2 and (a xor b)
//			}
//			else {
//				result.setBit(i, result.getBit(i).xor(temp)); // a xor b xor borrow2
//				borrow2.setBit(i, (a.getBit(i).xor(b.getBit(i))).not().and(temp)); // not borrow2 and (a xor b)
//			}
//			borrow2.setBit(i, borrow1.getBit(i).or(borrow2.getBit(i)));
//		}

		Longword one_L = new Longword();
		Longword temp = new Longword();
		temp.copy(b);
		one_L.set(1);
		Bit zero = new Bit();
		Bit one = new Bit();
		one.set(1);
		for (int i=0; i<32; i++){
			if (temp.getBit(i).getValue() == 0){
				temp.setBit(i, one);
			}
			else {
				temp.setBit(i, zero);
			}
		}
		temp = add(temp, one_L);
		result = add(a, temp);
		return result;
	}
}

