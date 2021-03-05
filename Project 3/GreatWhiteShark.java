public class GreatWhiteShark extends Fish implements Endangered {

    private final String DESCRIPTION = "Great White Shark";
    private static final boolean BREATHES_AIR = false;
    private static final BirthType BIRTH_TYPE = BirthType.LIVE_BIRTH;

    public GreatWhiteShark (int id, String name) {
        super(id, name, BIRTH_TYPE);
    }

    public String getDescription() {
        return String.format("%s\t%s\t%s", super.getDescription(), DESCRIPTION, isEndangeredToString());
    }

    @Override
    public boolean breathesAir() {
        return BREATHES_AIR;
    }

    public void displayConservationInformation() {
        System.out.println(String.format("%s is an endangered animal.", getName()));
    }
}
