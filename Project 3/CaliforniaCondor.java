public class CaliforniaCondor extends Bird implements Endangered {

    private static final boolean CAN_FLY = true;
    private final String DESCRIPTION = "California Condor";

    CaliforniaCondor (int id, String name) {
        super(id, name);
    }

    public String getDescription() {
        return String.format("%s\t%s", super.getDescription(), DESCRIPTION);
    }

    public boolean canFly() {
        return CAN_FLY;
    }

    public void displayConservationInformation() {
        System.out.println(String.format("%s is an endangered animal.", getName()));
    }
}
