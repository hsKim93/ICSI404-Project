/* 
 Created by: Hyungsuk Kim
 Created on: 8/24/2020
 email - hkim29@albany.edu 
 */
package the_bit;

public class Bit_test {

	// 2 bits for test runs
	private Bit bit1 = new Bit();
	private Bit bit2 = new Bit();

	// counters for pass/fail percentage
	private double pass = 0;
	private double counter = 0;

	// compares actual value and expected value and prints the result
	// increments pass, counter upon successful condition
	public void result(int actual, int expected) {

		if(actual == expected) {
			System.out.println("Pass\n");	
			pass++;
		}
		else {
			System.out.println("Fail\n");
		}
		counter ++;
	}



	public void runTests() {

		// bit1 = 0
		// bit2 = 1
		System.out.println("void set(int value)");
		bit1.set(0);
		result(bit1.getValue(), 0);
		bit2.set(1);
		result(bit2.getValue(), 1);

		// bit1 = 1
		// bit2 = 0
		System.out.println("\nvoid toggle()");
		bit1.set(0); 
		bit1.toggle(); 
		result(bit1.getValue(), 1);		
		bit2.set(1);
		bit2.toggle(); 
		result(bit2.getValue(), 0);

		// bit1 = 1
		System.out.println("\nvoid set()");
		bit1.set();  
		result(bit1.getValue(), 1);

		// bit 1 = 0;
		System.out.println("\nvoid clear()");
		bit1.clear();  
		result(bit1.getValue(), 0);

		// bit 1 = 0;
		System.out.println("\nint getValue()");
		bit1.set(); 
		result(bit1.getValue(), 1);
		bit1.clear();
		result(bit1.getValue(), 0);

		// bit 1 = 0;
		// bit 2 = 1;
		System.out.println("\nbit and(bit other)");
		bit1.clear();
		bit2.set();
		result(bit1.and(bit1).getValue(), 0); // 00 = 0
		result(bit1.and(bit2).getValue(), 0); // 01 = 0
		result(bit2.and(bit1).getValue(), 0); // 10 = 0
		result(bit2.and(bit2).getValue(), 1); // 11 = 1


		// bit 1 = 0;
		// bit 2 = 1;
		System.out.println("\nbit or(bit other)");
		bit1.clear();
		bit2.set();
		result(bit1.or(bit1).getValue(), 0); // 00 = 0
		result(bit1.or(bit2).getValue(), 1); // 01 = 1
		result(bit2.or(bit1).getValue(), 1); // 10 = 1
		result(bit2.or(bit2).getValue(), 1); // 11 = 1

		// bit 1 = 0;
		// bit 2 = 1;
		System.out.println("\nbit xor(bit other)");
		bit1.clear();
		bit2.set();
		result(bit1.xor(bit1).getValue(), 0); // 00 = 0
		result(bit1.xor(bit2).getValue(), 1); // 01 = 1
		result(bit2.xor(bit1).getValue(), 1); // 10 = 1
		result(bit2.xor(bit2).getValue(), 0); // 11 = 0

		// bit 1 = 0;
		// bit 2 = 1;
		System.out.println("\nString toString()");
		if(bit1.toString().equals("0")) {
			System.out.println("pass");
			pass++;
		}
		else
			System.out.println("fail");
		counter++;

		if(bit2.toString().equals("1")) {
			System.out.println("pass");
			pass++;
		}
		else
			System.out.println("fail");
		counter++;

		// percentage 
		System.out.println("\n" + pass/counter * 100 + "% passed");

	}
}
