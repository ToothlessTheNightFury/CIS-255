public abstract class Mammal extends Animal {

    private final boolean IS_WARM_BLOODED = true;
    private final String DESCRIPTION = "Mammal";

    public Mammal (int id, String name, BirthType birthType) {
        super(id, name, birthType);
    }

    public String getDescription() {
        return String.format("%s\t%s", ANIMAL_DESCRIPTION, DESCRIPTION);
    }

    @Override
    public boolean isWarmBlooded() {
        return IS_WARM_BLOODED;
    }
}
