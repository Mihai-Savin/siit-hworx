/**
 * 
 */
package ro.mihaisavin.automotive;

/**
 * @author Mihai Savin 28.11.2015
 *
 */
public class NotEnoughFuelException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NotEnoughFuelException(String message) {
		super(message);
	}
	public NotEnoughFuelException() {
		super();
	}

}
