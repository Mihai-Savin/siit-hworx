package ro.mihaisavin.automotive;

/**
 * This App fools around with some random cars that are parked and retrieved
 * from the ValletParking. Some tickets are not used. This means that the
 * vehicles corresponding to these are in the parking.
 * 
 * @author Me
 *
 */
public class ParkingApp {

	public static void main(String[] args) {

		ValletParking<Car> carParking = new ValletParking<Car>(5);

		System.out.println("Parking OPEN.");

		ParkingTicket firstTicket = carParking.park(new Golf(50, "S1OME123CH99NR"));
		ParkingTicket secondTicket = carParking.park(new Logan(50, "S2OME123CH99NR"));
		ParkingTicket thirdTicket = carParking.park(new Logan(50, "S3OME123CH99NR"));
		ParkingTicket fourthTicket = carParking.park(new Logan(50, "S4OME123CH99NR"));
		ParkingTicket fifthTicket = carParking.park(new Logan(50, "S5OME123CH99NR"));

		Vehicle aVehicle = carParking.retrieve(secondTicket);
		Vehicle anotherVehicle = carParking.retrieve(fifthTicket);

		secondTicket = carParking.park(new Logan(50, "S99OME123CH99NR"));
		secondTicket = carParking.park(new Logan(50, "S89OME123CH99NR"));
		secondTicket = carParking.park(new Logan(50, "S19OME123CH99NR"));

		carParking.printAllVehicles();

		ValletParking<Vehicle> parkingZorilor = new ValletParking<>(2);

		System.out.println("Parking OPEN.");

		firstTicket = parkingZorilor.park(new Helicopter("MyHelyPoney"));
		secondTicket = parkingZorilor.park(new Logan(50, "S89OME123CH99NR"));
		secondTicket = parkingZorilor.park(new Logan(50, "S19OME123CH99NR"));

	}

}
