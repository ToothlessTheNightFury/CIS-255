public class BlueWhale extends Whale implements Endangered {

    private final String DESCRIPTION = "Blue Whale";

    public BlueWhale (int id, String name) {
        super(id, name);
    }

    public String getDescription() {
        return String.format("%s\t%s\t%s", super.getDescription(), DESCRIPTION, isEndangeredToString());
    }

    public void displayConservationInformation() {
        System.out.println(String.format("%s is an endangered animal.", getName()));
    }
}
