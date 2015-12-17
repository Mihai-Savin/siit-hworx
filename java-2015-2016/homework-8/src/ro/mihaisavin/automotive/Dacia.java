package ro.mihaisavin.automotive;
/**
 * 
 */

/**
 * @author Mihai Savin
 *
 */
public abstract class Dacia extends Car {
	public Dacia(float fuelAmount, String chassisNumber) {
		super(fuelAmount, chassisNumber);
		manufacturerName = "Dacia-Renault Automobile S.A.";		
		manufacturerLicense = "All type of automobiles";
	}
	public Dacia() {	
		super();
		manufacturerName = "Dacia-Renault Automobile S.A.";		
		manufacturerLicense = "All type of automobiles";
	}
	
}