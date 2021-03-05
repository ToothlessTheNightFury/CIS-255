/* ADD THE EQUALS METHOD */
/* IMPLEMENT THE COMPARABLE INTERFACE */
/* MAKE NO OTHER CHANGES TO THIS FILE */

import java.text.Collator;

public abstract class Animal implements Comparable<Animal> {

	private int id;
	private String name;
	private BirthType birthType;

	public static final String ANIMAL_DESCRIPTION = "Animal";

	public Animal(int id, String name, BirthType birthType) {
		this.id = id;
		this.name = name;
		this.birthType = birthType;
	}
	
	
	// information about birth type
	public final boolean laysEggs() {
		return birthType==BirthType.LAYS_EGGS;
	}
	public final boolean hasLiveBirth() {
		return birthType==BirthType.LIVE_BIRTH;
	}
	public final BirthType getBirthType() {
		return birthType;
	}

	// getters (no setters provided)
	public final int getId() {
		return id;
	}

	public final String getName() {
		return name;
	}

	// toString method will create text with name, id, birth type, warm/cold blooded, and the description
	@Override
	public final String toString() {
		return name + " (id=" + id + "):\t" + birthType.toString() + "\t" +
				(isWarmBlooded() ? "Warm Blooded" : "Cold Blooded") + "\t" +
				getDescription();
	}

	@Override
	public final boolean equals (Object obj) {
		if (obj instanceof Animal) {
			Animal other = (Animal) obj;
			return (id == other.id) && (name.equalsIgnoreCase(other.getName()));
		}

		return false;
	}

	public int compareTo (Animal obj) {

		// Collator used to provide alphabetic sorting for ALL languages
		// If Unicode values compared, only works for English
		Collator collator = Collator.getInstance();

		return collator.compare(name, obj.name);
	}

	// abstract methods to implement in child classes
	public abstract boolean isWarmBlooded();
	public abstract String getDescription();
	
}
