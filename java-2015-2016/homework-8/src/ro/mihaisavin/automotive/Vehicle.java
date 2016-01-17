package ro.mihaisavin.automotive;
/**
 * 
 */

/**
 * @author Mihai Savin
 *
 */
public interface Vehicle {
	
	/**
	 * Starts the Vehicle
	 */
	public void start();

	/**
	 * Drives for a specified distance
	 * @param distance
	 *            specifies the exact distance to drive, measured in kilometers
	 * @throws NotEnoughFuelException 
	 */
	public void drive(float distance) throws NotEnoughFuelException; // drives the Vehicle

	
	public void stop(); // stops the Vehicle
}