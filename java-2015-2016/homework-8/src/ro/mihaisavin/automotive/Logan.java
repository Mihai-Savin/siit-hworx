package ro.mihaisavin.automotive;

/**
 * Models a Logan car
 * 
 * @author Mihai Savin
 *
 */
public class Logan extends Dacia {

	public Logan(float fuelAmount, String chassisNumber) {
		super(fuelAmount, chassisNumber);
	
		fuelType = FUEL_TYPES[0]; // petrol engine
		standardAverageFuelConsumption = 4.7f;
		averagePollution = 30; // gramms per KM
		tankSize = 50;
		currentGear = 1;
	}

	public Logan() {
		super();
	}

	protected float consume(float distance) { //
		float amountConsumed = distance / 100 * standardAverageFuelConsumption; // average
		// fuel
		// consumed
		switch (this.currentGear) { // Optimized for most often usage of gears
		case Car.Gears.NEUTRAL:
			return 0f; // No consumption. In reality should be some liters/hour
		case Car.Gears.FIRST:
		case Car.Gears.REVERSE:
			return (float) (amountConsumed * 2.0736); // +107%
		case Car.Gears.THIRD:
			return (float) (amountConsumed * 1.44); // +44%
		case Car.Gears.SECOND:
			return (float) (amountConsumed * 1.728); // +72.8%
		case Car.Gears.FOURTH:
			return (float) (amountConsumed * 1.2); // +20%
		case Car.Gears.FIFTH:
			return (float) amountConsumed; // 5th gear has average consumption
		default:
			System.out.println("This will never be printed as no other gear is possible.");
			return -1;
		}
	}

}