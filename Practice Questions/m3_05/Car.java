public class Car {

    protected String m_name;
    protected String m_model;
    protected int m_year;

    public Car() {
        this("", "", 0);
    }

    public Car (String name, String model, int year) {
        m_name = name;
        m_model = model;
        m_year = year;
    }

    public String getName() {
        return m_name;
    }

    public void setName (String name) {
        m_name = name;
    }

    public String getModel() {
        return m_model;
    }

    public void setModel (String model) {
        m_model = model;
    }

    public int getYear() {
        return m_year;
    }

    public void setYear (int year) {
        m_year = year;
    }

    public void printFullInfo() {
        System.out.printf("%s\n%s\n%s\n\n", m_name, m_model, m_year);
    }

    public static void main (String[] args) {

        Car uninitializedCar;
        Car defaultCar = new Car();
        Car parameterizedCar = new Car("Ford", "Mustang", 2020);

        parameterizedCar.printFullInfo();

        System.out.printf("getName(): %s\n", parameterizedCar.getName());
        System.out.printf("getModel(): %s\n", parameterizedCar.getModel());
        System.out.printf("getYear(): %s\n\n", parameterizedCar.getYear());

        parameterizedCar.setName("Lamborghini");
        parameterizedCar.setModel("Aventador SVJ");
        parameterizedCar.setYear(2020);

        parameterizedCar.printFullInfo();
    }
}
