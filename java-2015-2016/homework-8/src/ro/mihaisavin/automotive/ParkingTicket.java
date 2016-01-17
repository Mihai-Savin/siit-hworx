/**
 * 
 */
package ro.mihaisavin.automotive;

/**
 * @author Me
 *
 */
public class ParkingTicket {
	/**
	 * is unique for entire parking log
	 */
	int iD;

	// could be useful to register also the car license plates and the our of
	// entrance to the parking

	/**
	 * Generates a ParkingTicket
	 * @param iD
	 */
	public ParkingTicket(int iD) {
		this.iD = iD;
	}

	/**
	 * Returns the ticket ID.
	 * @return
	 */
	public int getiD() {
		return iD;
	}

}
