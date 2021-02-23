public class Surgeon extends Doctor {

    String specialty = "Surgeon";

    public Surgeon(String name) {
        super(name);
    }

    public String getSpecialty() {
        return specialty;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nSpecialty: %s", getName(), getSpecialty());
    }
}