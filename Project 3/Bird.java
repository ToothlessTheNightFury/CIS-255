public abstract class Bird extends Animal implements Winged  {

    private static final BirthType BIRTH_TYPE = BirthType.LAYS_EGGS;
    private final boolean IS_WARM_BLOODED = true;
    private final String DESCRIPTION = "Bird";

    public Bird (int id, String name) {
        super(id, name, BIRTH_TYPE);
    }

    public String getDescription() {
        return String.format("%s\t%s\t%s", ANIMAL_DESCRIPTION, DESCRIPTION, canFlyToString());
    }

    @Override
    public boolean isWarmBlooded() {
        return IS_WARM_BLOODED;
    }
}
