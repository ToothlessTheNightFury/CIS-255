public class Doctor {

    private String name = "";

    public Doctor (String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Name: %s", getName());
    }
}