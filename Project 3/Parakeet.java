public class Parakeet extends Bird {

    private static final boolean CAN_FLY = true;
    private final String DESCRIPTION = "Parakeet";

    Parakeet (int id, String name) {
        super(id, name);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }

    public boolean canFly() {
        return CAN_FLY;
    }
}
