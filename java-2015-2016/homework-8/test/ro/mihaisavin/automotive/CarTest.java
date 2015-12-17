package ro.mihaisavin.automotive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ro.mihaisavin.automotive.Car.Gears;

public class CarTest {

	Car testCar;

	@Before
	public void setup() {
		testCar = new Logan(4.7f, "SAV123");
	}

	@Test
	public void checkCarConstruction_dummyCar_justForTheFunOfIt() {
		Car aCar = new Logan();
		assertNotNull(aCar);
	}

	@Test
	public void checkCarConstruction_validParams() {
		Car aCar = new Logan(10, "SAAVVZZ123ASDFG");
		assertEquals(new Float(10), new Float(aCar.fuelAmount));
		assertEquals("SAAVVZZ123ASDFG", aCar.chassisNumber);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkCarConstruction_negativeFuelAmount() {
		new Logan(-1, "SAAVVZZ123ASDFG");
	}

	@Test
	public void checkCarConstruction_emptyChassisNumber_3Way() {
		try {
			new Logan(10, "");
			fail("You should not be able to create a car with no chassis number.");
			// this line should not be reached
		} catch (IllegalArgumentException ex) {
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkCarConstruction_emptyChassisNumber_4Way() {
		new Logan(10, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkCarConstruction_nullChassisNumber() {
		new Logan(10, null);
	}

	@Test
	public void checkShiftGear_validGear() {
		testCar.shiftGear(Car.Gears.FIRST);
		assertEquals(testCar.currentGear, Car.Gears.FIRST);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkShiftGear_randomGear() {
		testCar.shiftGear(99);
	}

	@Test
	public void checkGearCheckerForStarting_validGears() {
		for (int i = -1; i < 2; i++) { // iterates trough gears REVERSE,
										// NEUTRAL, and FIRST
			testCar.shiftGear(i);
			if (!testCar.checkGearForStarting()) {
				fail();
			}
		}
	}

	@Test
	public void checkGearCheckerForStarting_invalidExistentGear() {
		testCar.shiftGear(Car.Gears.SECOND);
		if (testCar.checkGearForStarting()) {
			fail();
		}
	}

	@Test
	public void checkGearCheckerForStarting_invalidInexistentGear_1() {
		testCar.currentGear = 99;
		if (testCar.checkGearForStarting()) {
			fail();
		}
	}

	@Test
	public void checkGearCheckerForStarting_invalidInexistentGear_2() {
		testCar.currentGear = -99;
		if (testCar.checkGearForStarting()) { // "scris cu picioru" way
			fail();
		}
		assertFalse(testCar.checkGearForStarting()); // elegant way
	}

	@Test
	public void checkStart_validState() {
		testCar.start();
		assertTrue(testCar.started);
	}

	@Test
	public void checkStart_wrongGear() {
		testCar.shiftGear(4);
		testCar.start();
		assertFalse(testCar.started);
	}

	@Test
	public void checkStart_alienGear1() {
		testCar.currentGear = 1982; // normally this should not be in normal
									// conditions, but who knows :D
		testCar.start();
		assertFalse(testCar.started);
	}

	@Test
	public void checkStart_alienGear2() {
		testCar.currentGear = -Integer.MAX_VALUE; // normally this should not be
													// in normal
		// conditions, but who knows :D
		testCar.start();
		assertFalse(testCar.started);
	}

	@Test
	public void checkDrive_drivableGear() throws NotEnoughFuelException {
		// Should increment the distance that the car has driven.
		testCar.start();
		float initialOdometerIndex = testCar.distance;
		testCar.shiftGear(Gears.FIRST);
		testCar.drive(10); // drives for exactly 10 km
		float expected = initialOdometerIndex + 10;
		float actual = testCar.distance;
		// assertEquals(expected, actual); // Why does this deprecated method
		// fail?!?
		assertEquals(expected, actual, 0);
	}

	@Test(expected = IllegalStateException.class)
	public void checkDrive_undrivableGear() throws NotEnoughFuelException {
		// Should not be able to drive (driving is impossible if no gear is
		// selected). Should throw exception.
		testCar.start();
		testCar.shiftGear(Gears.NEUTRAL);
		testCar.drive(10);
	}

	@Test(expected = NotEnoughFuelException.class)
	public void checkDrive_distanceTooLong_insufficientFuel() throws NotEnoughFuelException {
		// should throw exception
		float tourOfTheWorld_times100 = 12742 * 100; // a lot of Kilometers :)
		testCar.start();
		testCar.shiftGear(Gears.FIFTH); // Why does this WORK while
		// testCar.shiftGear(FIRST); //this does NOT work?
		testCar.drive(tourOfTheWorld_times100);
	}

	@Test
	public void checkGetSAFuelConsumption() {
		// Nothing to test really. It's a simple getter so as it was stated
		// during the course presentation I think it's not
		// the case to test something like this,
		//
		// or the other getters either.
		//
		// Right?!
		// But still!!
		float expected = 4.7f; // this value is hardcoded for the
								// Car-Dacia-Logan
		assertEquals(expected, testCar.getStandardAverageFuelConsumption(), 0);
	}
	
	@Test
	public void checkStop() {
		testCar.stop();
		assertFalse(testCar.started);
	}

	//
	//
	//
	//

	@Test
	public void checkNothing() {
		System.out.println("\nIf you can read this on the console \nit means that all went well. :)");

	}

}
