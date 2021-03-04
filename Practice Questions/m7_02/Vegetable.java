public class Vegetable extends Food {

	private final boolean DEFAULT_IS_GREEN = true;
	private boolean isGreen;

	public Vegetable (String name) {
		this(name, DEFAULT_IS_GREEN);
	}

	public Vegetable (String name, boolean isGreen) {
	    this.name = name;
	    this.isGreen = isGreen;
	}

	public String getName() {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public boolean getIsGreen() {
		return isGreen;
	}

	public void setIsGreen (boolean isGreen) {
		this.isGreen = isGreen;
	}

	@Override
	public String toString() {
		return String.format("%s (Vegetable%s)", getName(), getIsGreen() ? ", Green" : "");
	}

	@Override
	public boolean equals (Object obj) {
		
		if (obj instanceof Vegetable) {

			Vegetable other = (Vegetable) obj;
			return getName().equals(other.getName()) && getIsGreen() == other.getIsGreen(); 
		}

		return false;
	}
}
