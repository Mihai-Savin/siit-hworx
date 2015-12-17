package ro.mihaisavin.automotive;

import java.util.ArrayList;
import java.util.List;

public class ValletParking<T extends Vehicle> {

	private List<T> parkingRegistry = new ArrayList<T>();
	private int parkingSize;

	/**
	 * Constructs a ValletParking that has a specified number of parking slots.
	 * 
	 * @param size
	 */
	public ValletParking(int size) {
		parkingSize = size;
	}

	/**
	 * Parks a Vehicle and returns a ParkingTicket in order to retrieve the
	 * Vehicle.
	 * 
	 * @param aVehicle
	 * @return
	 */
	public ParkingTicket park(T aVehicle) { // should return a Ticket for
											// parking the Vehicle
		checkAllreadyParked(aVehicle);

		if (!freeSpaces()) {
			System.out.println("Parking full. Cannot park " + aVehicle);
			return null;
		}

		int location = -1; // if this flag is -1 park @ next default space

		System.out.println("\nVehicle received. Parking vehicle...");

		// check for liberated parking spaces
		for (int i = 0; i < parkingRegistry.size(); i++) {
			if (parkingRegistry.get(i) == null) {
				location = i; // vehicle will be parked at this slot
				break;
			}
		}

		aVehicle.start();
		try {
			aVehicle.drive(0.5f);
		} catch (NotEnoughFuelException e) {
			e.printStackTrace();
		}
		aVehicle.stop();
		if (location == -1) { // default parking procedure - no vehicles left
								// from parking
			parkingRegistry.add(aVehicle);
			location = parkingRegistry.size();
		} else { // park at some liberated slot - some vehicle left the parking
			parkingRegistry.set(location, aVehicle);
		}

		System.out.println("Vehicle parked @ spot #" + location + "\n");

		return new ParkingTicket(parkingRegistry.size());
	}

	private boolean freeSpaces() {
		if (parkingRegistry.size() == parkingSize && !parkingRegistry.contains(null)) {
			return false;
		}
		return true;
	}

	/**
	 * Check if someone tries to do something that is impossible e.g. park a
	 * vehicle that is already parked
	 * 
	 * @param aVehicle
	 */
	private void checkAllreadyParked(T aVehicle) {

		for (T someVehicle : parkingRegistry) {
			if (aVehicle.equals(someVehicle)) {
				throw new IllegalArgumentException("Vehicle allready inside parking.");
			}
		}
		// Can be done with
		// parkingRegistry.contains(aVehicle); too,
		// but I preferred it this way right now. :)

	}

	/**
	 * Retrieves a Vehicle from the Parking based on the ParkingTicket.
	 * 
	 * @param aTicket
	 * @return
	 */
	public T retrieve(ParkingTicket aTicket) {
		T vehicle;

		if (aTicket == null) {
			System.out.println("Invalid ticket.");
			return null;
		}

		System.out.println("\nTicket #" + aTicket.getiD() + " received. Fetching vehicle...");

		Vehicle aVehicle = parkingRegistry.get(aTicket.getiD() - 1);

		aVehicle.start();
		try {
			aVehicle.drive(0.5f);
		} catch (NotEnoughFuelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aVehicle.stop();

		System.out.println("Vehicle parked @ spot #" + aTicket.getiD() + " at your disposal.");
		System.out.println(aVehicle + "\n");

		vehicle = parkingRegistry.get(aTicket.getiD() - 1);
		parkingRegistry.set(aTicket.getiD() - 1, null);
		return vehicle;
	}

	/**
	 * Prints out to the console all the Vehicles that are inside of the Parking.
	 */
	public void printAllVehicles() {
		System.out.println("\nVehicles in parking right now:");
		for (T someVehicle : parkingRegistry) {
			System.out.println(someVehicle);
		}
	}

}
