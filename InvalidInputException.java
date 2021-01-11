package the_bit;

public class InvalidInputException extends Exception {
	
	public InvalidInputException() {
		super("Invalid input");
	}
	
	public InvalidInputException(String message) {
		super(message);
	}
	
}
