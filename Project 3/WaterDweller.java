interface WaterDweller {

    boolean breathesAir();

    default String breathesAirToString() {
        return String.format("(lives in water, %s)", breathesAir() ? "breathes air" : "does not breathe air");
    }
}