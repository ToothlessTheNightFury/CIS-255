interface Winged {

    boolean canFly();

    default String canFlyToString() {
        return String.format("(has wings, %s)", canFly() ? "can fly" : "cannot fly");
    }
}
