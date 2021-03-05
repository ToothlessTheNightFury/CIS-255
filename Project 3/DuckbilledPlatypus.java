public class DuckbilledPlatypus extends Mammal {

    private static final BirthType BIRTH_TYPE = BirthType.LAYS_EGGS;
    private final String DESCRIPTION = "Duck-billed Platypus";

    public DuckbilledPlatypus(int id, String name) {
        super(id, name, BIRTH_TYPE);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }
}
