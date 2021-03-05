public class Elephant extends Mammal implements Endangered {

    private static final BirthType BIRTH_TYPE = BirthType.LIVE_BIRTH;
    private final String DESCRIPTION = "Elephant";

    public Elephant (int id, String name) {
        super(id, name, BIRTH_TYPE);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }

    public void displayConservationInformation() {
        System.out.println(String.format("%s is an endangered animal.", getName()));
    }
}