public abstract class Whale extends Mammal implements WaterDweller {

    private final String DESCRIPTION = "Whale";
    private static final boolean BREATHES_AIR = true;
    private static final BirthType BIRTH_TYPE = BirthType.LIVE_BIRTH;

    public Whale (int id, String name) {
        super(id, name, BIRTH_TYPE);
    }

    public String getDescription() {
        return String.format("%s\t%s\t%s", super.getDescription(), DESCRIPTION, breathesAirToString());
    }

    public boolean breathesAir() {
        return BREATHES_AIR;
    }
}
