interface WaterDweller {

    boolean breathesAir();

    default String breathesAirToString() {
        return String.format("Water Dweller (%s)", breathesAir() ? "Air" : "Water");
    }
}