interface Winged {

    boolean canFly();

    default String canFlyToString() {
        return String.format("Winged (%s)", canFly() ? "Can Fly" : "Cannot Fly");
    }
}
