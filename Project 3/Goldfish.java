public class Goldfish extends Fish {

    private final String DESCRIPTION = "Goldfish";
    private static final boolean BREATHES_AIR = false;
    private static final BirthType BIRTH_TYPE = BirthType.LAYS_EGGS;

    public Goldfish (int id, String name) {
        super(id, name, BIRTH_TYPE);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }

    @Override
    public boolean breathesAir() {
        return BREATHES_AIR;
    }
}

