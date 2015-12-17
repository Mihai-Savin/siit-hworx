package ro.mihaisavin.automotive;

import ro.mihaisavin.automotive.Car.Gears;

/**
 * 
 */

/**
 * @author Me
 *
 */
public class Golf extends Volkswagen {
	
	
	public Golf() {
		super();
	}

	public Golf(float fuelAmount, String chassisNumber) {
		super(fuelAmount, chassisNumber);

		fuelType = FUEL_TYPES[1]; // diesel engine
		standardAverageFuelConsumption = 4.3f;
		tankSize = 55;
		averagePollution = 70; // gramms per KM - pollutes a whole LOT ! ;)
		currentGear = 1;
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
			return (float) (amountConsumed * 2.5); // +150%
		case Car.Gears.THIRD:
			return (float) (amountConsumed * 1.55); // +55%
		case Car.Gears.SECOND:
			return (float) (amountConsumed * 1.88); // +88%
		case Car.Gears.FOURTH:
			return (float) (amountConsumed * 1.162); // +16.2%
		case Car.Gears.FIFTH:
			return (float) amountConsumed; // 5th gear has average consumption
		default:
			System.out.println("This will never be printed as no other gear is possible.");
			return -1;
		}
	}
	
	
}