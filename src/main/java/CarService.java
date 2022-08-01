import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

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


}
