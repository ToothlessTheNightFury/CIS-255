public class Horse extends Mammal {

    private static final BirthType BIRTH_TYPE = BirthType.LIVE_BIRTH;
    private final String DESCRIPTION = "Horse";

    public Horse (int id, String name) {
        super(id, name, BIRTH_TYPE);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }
}