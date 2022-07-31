import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        CarService carService = new CarServiceImpl();

        carService.createCarTable();
        carService.createModelTable();
        carService.createManufacturerTable();
        carService.createManufacturerModelTable();
        carService.createModelCarTable();

        //carService.insertIntoCarTable(new Car("Car name1", "Car description1", CarType.CABRIO));

        Car car2 = new Car("Car name2", "Car description2", CarType.COUPE);
        Car car3 = new Car("Car name3", "Car description3", CarType.SEDAN);
        Car car4 = new Car("Car name4", "Car description4", CarType.SEDAN);
        Car car5 = new Car("Car name5", "Car description5", CarType.HATCHBACK);
        Car car6 = new Car("Car name6", "Car description6", CarType.COUPE);

        //carService.insertACarListIntoCarTable(List.of(car2,car3,car4,car5,car6));

        carService.allCars().forEach(car -> System.out.println(car));

        Model model1 = new Model("Model name1", 1995, List.of(carService.allCars().get(0),carService.allCars().get(1)));

        carService.insertAModelIntoModelTable(model1);

    }
}
