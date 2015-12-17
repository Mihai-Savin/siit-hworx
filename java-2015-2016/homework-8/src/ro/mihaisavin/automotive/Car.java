package ro.mihaisavin.automotive;

/**
 * @author Mihai Savin
 *
 */
/**
 * @author Me
 *
 */
public abstract class Car implements Vehicle {
	public interface Gears { // this is a shortcut for static final ;)
		int REVERSE = -1, //
				NEUTRAL = 0, //
				FIRST = 1, //
				SECOND = 2, //
				THIRD = 3, //
				FOURTH = 4, //
				FIFTH = 5; //
	}

	// I don't know how to refactor this block into the upper class Gears, so
	// for legacy/regression purposes I
	// leaved it as it is. Any ideas? 10x
	/**
	 * @deprecated since v0.2
	 * @see #this.Gears
	 */
	@Deprecated
	static final int FIRST = 1;
	@Deprecated
	static final int SECOND = 2;
	@Deprecated
	static final int THIRD = 3;
	@Deprecated
	static final int FOURTH = 4;
	@Deprecated
	static final int FIFTH = 5;
	@Deprecated
	static final int NEUTRAL = 0;
	@Deprecated
	static final int REVERSE = -1;
	// End of legacy block

	protected static final String[] FUEL_TYPES = { "PETROL", "DIESEL", "HYBRID", "ELECTRIC" };
	protected int tankSize;
	protected String fuelType;
	protected int numOfGears = 6;
	protected int currentGear = Gears.NEUTRAL;
	protected float standardAverageFuelConsumption; // liters per 100 KM
	protected float averagePollution; // pollution per KM
	protected float fuelAmount; // existent quantity of fuel in tank
	protected String chassisNumber;
	protected String manufacturerName;
	protected String manufacturerLicense;
	/**
	 * distance driven from last start of car
	 */
	protected float distance;
	/**
	 * total consumption from since last start of car
	 */
	protected float consumption;
	/**
	 * total pollution since last start of car
	 */
	protected float pollution;
	protected boolean started = false;

	/**
	 * Default constructor.
	 * 
	 * @param fuelAmount2
	 *            indicates how much fuel to put in the tank
	 * @param chassisNumber
	 */
	public Car(float fuelAmount2, String chassisNumber) {
		if (fuelAmount2 < 0) {
			throw new IllegalArgumentException("Cannot create a Car with negative fuel amount.");
		}
		if ("".equals(chassisNumber) || chassisNumber == null) {
			throw new IllegalArgumentException("Cannot create a Car with null or empty chassis number");
		}

		this.fuelAmount = fuelAmount2;
		this.chassisNumber = chassisNumber;
	}

	/**
	 * Blank constructor.
	 */
	public Car() {
		this(0, "JUST A DUMMY CAR. NO CHASSIS NUMBER. FOR TESTING PURPOSES");
	}

	/**
	 * Checks for correct gear, needed to start the car. If the gear is not
	 * right, the car cannot be started, consequently displaying an error
	 * message and returning a false value.
	 * 
	 * @return true if in correct gear, false if in some other gear
	 */
	protected boolean checkGearForStarting() {
		if ((currentGear < Gears.REVERSE) || (currentGear > Gears.FIRST)) {
			System.out.println("Gear " + currentGear + " is not valid.\n"
					+ "Please make sure you are in the correct gear(NEUTRAL, FIRST or REVERSE)");
			// this line could System.exit(1) I chose to simply return an error
			// message in the following return statement
			return false;
		}
		return true;
	}

	/**
	 * Changes form current gear to the one passed as parameter
	 * 
	 * @param gear
	 */
	public void shiftGear(int gear) {
		if ((gear < this.numOfGears) && (gear >= Car.Gears.REVERSE)) { // checks
																		// for
			// errors
			currentGear = gear;
			System.out.println("Changing gear...\nCurrent gear is: " + this.currentGear);
		} else {
			System.out.println("Error: Invalid gear. Current gear is: " + this.currentGear);
			throw new IllegalArgumentException(gear + " is not a valid gear.");
		}

	}

	public void start() {
		if (!this.checkGearForStarting())
			return;
		this.distance = 0; // resets the total driven distance
		this.consumption = 0; // resets the total consumption since started
		this.pollution = 0; // resets the total pollution since started
		this.started = true;
		System.out.println(this.toString() + " started.");
	}

	/**
	 * Drives the Car for some distance defined as double - polymorphism
	 * 
	 * @throws NotEnoughFuelException
	 * 
	 */
	public void drive(float distance) throws NotEnoughFuelException {
		checkGearForDriving();
		checkFuelLevel();

		float consumedFuel; // this block check to see if there is enough fuel
							// to drive the specified distance I COULDNT EXTRACT
							// IT IN A SEPARATE METHOD

		consumedFuel = consume(distance);
		if (consumedFuel > this.fuelAmount) {
			System.out.println("Cannot drive so much, not enough fuel.");
			throw new NotEnoughFuelException("Insufficient fuel for " + distance + " kilometers.");
		}

		this.distance += distance;
		this.consumption += consumedFuel;
		this.fuelAmount -= consumedFuel;
		this.pollution += distance * this.averagePollution;
	}

	/**
	 * Checks to see if fuel is lower than minimum and critical fuel level. If
	 * fuel low there will be a warning. If fuel critical the car will not be
	 * able to drive anymore and the app will exit.
	 */
	protected void checkFuelLevel() {
		if (this.fuelAmount < 1) {
			System.out.println("Cannot drive anymore. Critical fuel level. Almost empty.");

			// return; //exits only the method
			System.out.println("Exiting app...");
			System.exit(1);

		} else if (this.fuelAmount < 10) {
			System.out.println("Low fuel level. Please refuel.");
		}
	}

	/**
	 * Checks to see if car is started and if current gear is valid for driving.
	 */
	protected void checkGearForDriving() {
		if (!started || currentGear == Car.Gears.NEUTRAL) { // chechs if car
															// is started
															// and in some
															// driveable
															// gear
			System.out.println("Car not started or not in a gear. Please correct this.");
			throw new IllegalStateException(
					"Cannot drive while engine OFF (Car not started)" + " or if current gear is NEUTRAL");
		}
	}

	/**
	 * Drives the Car for some distance defined as double - polymorphism
	 * 
	 * @param distance
	 * @throws NotEnoughFuelException
	 */
	public void drive(double distance) throws NotEnoughFuelException { // artifice
																		// for
																		// double
																		// type
																		// parameter
		this.drive((float) distance);
	}

	/**
	 * Stops the Vehicle
	 */
	public void stop() {
		if (!started) {
			System.out.println("Cannot stop car. It is not started.");
			return;
		}
		this.started = false;
		System.out.println(this.toString() + " stopped.");
	}

	/**
	 * @return amount of available fuel in gas tank
	 */
	public float getAvailableFuel() {
		return this.fuelAmount;
	}

	public float getStandardAverageFuelConsumption() {
		return standardAverageFuelConsumption;
	}

	/**
	 * Calculates consumption depending on gear and distance and updates total
	 * car consumption and remaining quantity of fuel
	 * 
	 * @param distance
	 *            distance driven
	 * @return quantity of consumed fuel
	 */

	/**
	 * @param distance
	 * @return
	 */

	/**
	 * @return the average consumption since the last start of the car
	 */
	public float getAverageFuelConsumption() {
		if (consumption != 0) {
			return consumption / distance * 100; // returns the average
													// consumption since the
													// last start of the car
		} else {
			System.out.println("Car has not consumed anything.");
			return 0f;
		}

	}

	/**
	 * @return the average pollution of the car
	 */
	public float getPollution() {
		return averagePollution;
	}

	/**
	 * @return the pollution amount since the last start
	 */
	public float getTotalPollution() {
		return distance * averagePollution;
	}

	/**
	 * Calculates consumption depending on gear and distance and updates total
	 * car consumption and remaining quantity of fuel
	 * 
	 * @param distance
	 *            distance driven
	 * @return quantity of consumed fuel
	 */
	/**
	 * @param distance
	 * @return
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chassisNumber == null) ? 0 : chassisNumber.hashCode());
		result = prime * result + ((fuelType == null) ? 0 : fuelType.hashCode());
		result = prime * result + ((manufacturerLicense == null) ? 0 : manufacturerLicense.hashCode());
		result = prime * result + ((manufacturerName == null) ? 0 : manufacturerName.hashCode());
		result = prime * result + numOfGears;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (chassisNumber == null) {
			if (other.chassisNumber != null)
				return false;
		} else if (!chassisNumber.equals(other.chassisNumber))
			return false;
		if (fuelType == null) {
			if (other.fuelType != null)
				return false;
		} else if (!fuelType.equals(other.fuelType))
			return false;
		if (manufacturerLicense == null) {
			if (other.manufacturerLicense != null)
				return false;
		} else if (!manufacturerLicense.equals(other.manufacturerLicense))
			return false;
		if (manufacturerName == null) {
			if (other.manufacturerName != null)
				return false;
		} else if (!manufacturerName.equals(other.manufacturerName))
			return false;
		if (numOfGears != other.numOfGears)
			return false;
		return true;
	}

	/**
	 * Consumes a specific amount of fuel depending on the distance and the
	 * current gear.
	 * 
	 * @param distance
	 * @return
	 */
	protected abstract float consume(float distance);

	@Override
	public String toString() {
		return "Car details: chassisNumber=" + chassisNumber + " [fuelAmount=" + fuelAmount + " manufacturerName="
				+ manufacturerName + "]";
	}

	/**
	 * Calculates consumption depending on gear and distance and updates total
	 * car consumption and remaining quantity of fuel
	 * 
	 * @param distance
	 *            distance driven
	 * @return quantity of consumed fuel
	 */
	/**
	 * @param distance
	 * @return
	 */

}