public class BelugaWhale extends Whale {

    private final String DESCRIPTION = "Beluga Whale";

    public BelugaWhale (int id, String name) {
        super(id, name);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }
}