public abstract class Vehicle {
	boolean isStarted;

	// Enables the vehicle's movement system
	public void start() {
		isStarted = true;
	}

	// Disables the vehicle's movement system
	public void stop() {
		isStarted = false;
	} 

	/* Accelerates the vehicle. How much vehicle accelerates depends on vehicle and
	 * magnitude given (how much the user throttles). Returns the current acceleration 
	 * of vehicle.
	*/
	public abstract double accelerate (double magnitude);
}

public abstract class Car extends Vehicle implements Drivable {
	int gear = 1;
	double direction;
	double maneuverability;

	public int gearUp() {
		return ++gear;
	}

	public int gearDown() {
		return --gear;
	}

	/* Enables the breaks to decelerate the car. How much vehicle decelerates depends on
	 * vehicle and magnitude given (how hard user presses breaks). Returns current acceleration
	 * of car.
	*/
	public abstract double break (double magnitude);
}

public class ElectricCar extends Car {
	...
}

public class Truck extends Car implements Hitchable {
	Trailer trailer;
}

interface Drivable {

	/* Turns the vehicle toward different directions. How much vehicle turns depends on
	 * vehicle and the number of degrees turned from rest (pointed straight). Counterclockwise
	 * is positive and clockwise is negative.
	*/
	public abstract void steer (double degreesFromRest);
}

interface Hitchable {

	/* Hitches a trailer onto the vehicle. Note that the vehicle's acceleration should have
	 * their accelerate() function take the mass of the trailer into account.
	*/
	public abstract hitch (Trailer trailer);
}
