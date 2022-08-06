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

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.allCars().forEach(car -> System.out.println(car));

        Model model1 = new Model("Model name1", 1995, List.of(carService.allCars().get(0),carService.allCars().get(1)));
        Model model2 = new Model("Model name2", 1985, List.of(carService.allCars().get(0),carService.allCars().get(1),carService.allCars().get(2)));
        Model model3 = new Model("Model name3", 1975, List.of(carService.allCars().get(3),carService.allCars().get(5)));
        Model model4 = new Model("Model name4", 2005, List.of(carService.allCars().get(5),carService.allCars().get(4)));
        Model model5 = new Model("Model name5", 2015, List.of(carService.allCars().get(0),carService.allCars().get(4)));

        //carService.insertAModelIntoModelTable(model1);

        //carService.insertAModelListIntoModeTable(List.of(model2, model3, model4, model5));

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.allModels().forEach(model -> System.out.println(model));

        Manufacturer manufacturer1 = new Manufacturer("Manufacturer name1", 2004, List.of(carService.allModels().get(0),carService.allModels().get(1)));
        Manufacturer manufacturer2 = new Manufacturer("Manufacturer name2", 1994, List.of(carService.allModels().get(1),carService.allModels().get(3)));
        Manufacturer manufacturer3 = new Manufacturer("Manufacturer name3", 1975, List.of(carService.allModels().get(2),carService.allModels().get(4)));
        Manufacturer manufacturer4 = new Manufacturer("Manufacturer name4", 1945, List.of(carService.allModels().get(1),carService.allModels().get(4)));
        Manufacturer manufacturer5 = new Manufacturer("Manufacturer name5", 2015, List.of(carService.allModels().get(1),carService.allModels().get(2),carService.allModels().get(3)));

        //carService.insertAManufacturerIntoManifacturerTable(manufacturer1);

        //carService.insertAManufacturerListIntoManifacturerTable(List.of(manufacturer2,manufacturer3,manufacturer4, manufacturer5));

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.allManufacturer().forEach(manufacturer -> System.out.println(manufacturer));

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.allManufacturerNames().forEach(s -> System.out.println(s));

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.allManufacturersEstablishmentYears().forEach((s, integer) -> System.out.println(s + " : " + integer));

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.allModelNames().forEach(s -> System.out.println(s));

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.allYearsOfStartingProductionOfModels().forEach((s, integer) -> System.out.println(s + " : " + integer));

        System.out.println("----------------------------------------------------------------------------------------------");

        carService.carsOfSEDANTypeFromAModelNewerThan2019AndTheManufacturersFoundingYearLessThan1919().forEach(car -> System.out.println(car));

        //System.out.println(carService.carsOfSEDANTypeFromAModelNewerThan2019AndTheManufacturersFoundingYearLessThan1919().size());

    }
}
