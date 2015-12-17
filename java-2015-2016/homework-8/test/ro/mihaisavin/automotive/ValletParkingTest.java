package ro.mihaisavin.automotive;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ValletParkingTest {

	ValletParking<Car> aValletParking;

	@Before
	public void setup() {
		aValletParking = new ValletParking<>(2);
	}

	@Test
	public void checkPark_valid() {
		ParkingTicket aTicket = aValletParking.park(new Logan(10, "SOME123CHASSIS33NUMBER"));
		assertNotNull(aTicket);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkPark_invalid_sameCar() {
		aValletParking.park(new Logan(10, "SOME123CHASSIS33NUMBER"));
		aValletParking.park(new Logan(7, "SOME123CHASSIS33NUMBER"));
		// if equals would not be implemented this would fail
	}

	/**
	 * If parking is full no ticket (null) is issued.
	 */
	@Test
	public void checkPark_parkingFull() {
		aValletParking.park(new Logan(10, "SOME123CHASSIS33NUMBER"));
		aValletParking.park(new Logan(10, "SOME456CHASSIS33NUMBER"));
		ParkingTicket aTicket = aValletParking.park(new Logan(10, "SOME789CHASSIS33NUMBER"));
		assertNull(aTicket);
	}

	/**
	 * Takes into account that while the valet drove the car, the fuel amount
	 * decreased.
	 */
	@Test
	public void checkPark_someFuelIsConsumed() {
		Car aCar = new Logan(10, "SOME123CHASSIS33NUMBER");
		float amountOfFuelBeforeParking = aCar.getAvailableFuel();
		aValletParking.park(aCar);
		float amountOfFuelAfterParking = aCar.getAvailableFuel();
		assertTrue(amountOfFuelBeforeParking > amountOfFuelAfterParking);
	}

	/**
	 * Correct vehicle is retrieved from the parking.
	 */
	@Test
	public void checkRetrieve_valid() {
		ParkingTicket aTicket = aValletParking.park(new Logan(10, "SOME789CHASSIS33NUMBER"));
		Vehicle expectedVehicle = new Logan(10, "SOME789CHASSIS33NUMBER");
		Vehicle retrievedVehicle = aValletParking.retrieve(aTicket);
		assertEquals(expectedVehicle, retrievedVehicle);
	}

	/**
	 * If you have a invalid (null) ticket no Vehicle is retrieved (returns
	 * null).
	 */
	@Test
	public void checkRetrieve_invalidTicket() {
		ParkingTicket aTicket = null;
		Vehicle retrievedVehicle = aValletParking.retrieve(aTicket);
		assertNull(retrievedVehicle);
	}

	// @Test //This is compiling error, I guess there is no other way to test
	// this is there?!!?
	// public void checkSubaru() {
	// ValletParking<Subaru> aValletParking = new ValletParking<Subaru>(10);;
	// }

	@Test
	public void checkMyPrettyPony() {
		// creates a 10 slots parking for Ponies
		ValletParking<MyPrettyPony> aValletParking = new ValletParking<>(10);
		assertNotNull(aValletParking);
	}

	@Test
	public void checkHelicopterParking_valid() {
		// creates a 5 slots parking for Hellicopters
		ValletParking<Helicopter> aHelicopterParking = new ValletParking<>(5);
		assertNotNull(aHelicopterParking);

	}

}
