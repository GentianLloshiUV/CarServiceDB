import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CarService {

    void createCarTable() throws SQLException;
    void createModelTable() throws SQLException;
    void createManufacturerTable() throws SQLException;
    void createManufacturerModelTable() throws SQLException;

    void createModelCarTable() throws SQLException;

    void insertIntoCarTable(Car car) throws SQLException;

    void insertACarListIntoCarTable(List<Car> carList) ;

    List<Car> allCars() throws SQLException;

    void insertAModelIntoModelTable(Model model) throws SQLException;

    void insertAModelListIntoModeTable(List<Model> modelList) ;

    List<Car> carListFromModel(Integer modelID) throws SQLException;
    List<Model> allModels() throws SQLException;

    void insertAManufacturerIntoManifacturerTable(Manufacturer manufacturer) throws SQLException;
    void insertAManufacturerListIntoManifacturerTable(List<Manufacturer> manufacturerList);

    List<Model> modelListFromManufacturer(Integer id) throws SQLException;

    List<Manufacturer> allManufacturer() throws SQLException;

    List<String> allManufacturerNames() throws SQLException;

    Map<String, Integer> allManufacturersEstablishmentYears() throws SQLException;

    List<String>allModelNames() throws SQLException;
    Map<String, Integer> allYearsOfStartingProductionOfModels() throws SQLException;

    List<Car> carsOfSEDANTypeFromAModelNewerThan2019AndTheManufacturersFoundingYearLessThan1919() throws SQLException;




}
