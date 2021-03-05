public class Ostrich extends Bird {

    private static final boolean CAN_FLY = false;
    private final String DESCRIPTION = "Ostrich";

    Ostrich (int id, String name) {
        super(id, name);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }

    public boolean canFly() {
        return CAN_FLY;
    }
}