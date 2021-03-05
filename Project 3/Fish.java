public abstract class Fish extends Animal implements WaterDweller {

    private final boolean IS_WARM_BLOODED = false;
    private final String DESCRIPTION = "Fish";

    public Fish (int id, String name, BirthType birthType) {
        super(id, name, birthType);
    }

    public String getDescription() {
        return String.format("%s\t%s\t%s", ANIMAL_DESCRIPTION, DESCRIPTION, breathesAirToString());
    }

    @Override
    public boolean isWarmBlooded() {
        return IS_WARM_BLOODED;
    }
}
