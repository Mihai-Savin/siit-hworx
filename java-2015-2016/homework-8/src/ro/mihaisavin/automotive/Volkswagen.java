package ro.mihaisavin.automotive;
/**
 * 
 */

/**
 * @author Meehai Savin
 *
 */
public abstract class Volkswagen extends Car {
	public Volkswagen(float fuelAmount, String chassisNumber) {
		super(fuelAmount, chassisNumber);
		manufacturerName = "VAG - Volkswagen Audi Group GmbH";
		manufacturerLicense = "All type of automobiles & bycicles";
	}

	public Volkswagen() {
		super();
		manufacturerName = "VAG - Volkswagen Audi Group GmbH";
		manufacturerLicense = "All type of automobiles & bycicles";
	}

}