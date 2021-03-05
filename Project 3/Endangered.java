interface Endangered {

    void displayConservationInformation();

    default String isEndangeredToString() {
        return "Endangered";
    }
}

