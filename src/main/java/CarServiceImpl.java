import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService{

    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cars","root","toor");

    public CarServiceImpl() throws SQLException {
    }

    @Override
    public void createCarTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS car (car_id int primary key auto_increment,car_name varchar(255) not null,car_description varchar(255) not null,car_type enum('COUPE', 'CABRIO', 'SEDAN', 'HATCHBACK'));\n";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void createModelTable() throws SQLException {
        String sql ="CREATE TABLE IF NOT EXISTS model( model_id int primary key auto_increment, model_name varchar(255) not null, model_year int not null);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void createManufacturerTable() throws SQLException {
        String sql ="CREATE TABLE IF NOT EXISTS manufacturer( manufacturer_id int primary key auto_increment, manufacturer_name varchar(255) not null, manufacturer_year int not null);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void createManufacturerModelTable() throws SQLException {
        String sql ="CREATE TABLE IF NOT EXISTS manufacturer_model( id int primary key auto_increment,manufacturer_id int,model_id int, foreign key(model_id) references model(model_id), foreign key(manufacturer_id) references manufacturer(manufacturer_id));";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void createModelCarTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS model_car( id int primary key auto_increment,car_id int,model_id int, foreign key(model_id) references model(model_id),foreign key(car_id) references car(car_id));";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void insertIntoCarTable(Car car) throws SQLException {
        String sql = "insert into car(car_name, car_description, car_type)Values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, car.getName());
        preparedStatement.setString(2, car.getDescription());
        preparedStatement.setString(3, car.getCarType().toString());

        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void insertACarListIntoCarTable(List<Car> carList) {
        carList.forEach(car -> {
            try {
                insertIntoCarTable(car);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Car> allCars() throws SQLException {
        List<Car> output = new ArrayList<>();

        String sql = "SELECT * FROM cars.car;";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            output.add(new Car(resultSet.getInt("car_id"), resultSet.getString("car_name"), resultSet.getString("car_description"), CarType.valueOf(resultSet.getString("car_type"))));
        }

        resultSet.close();
        statement.close();

        return output;
    }

    @Override
    public void insertAModelIntoModelTable(Model model) throws SQLException {
        String sql = "INSERT INTO `cars`.`model`(`model_name`,`model_year`)VALUES(?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, model.getName());
        preparedStatement.setInt(2, model.getProductionStartYear());

        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();

        String sql2 = "INSERT INTO `cars`.`model_car`(`car_id`,`model_id`)VALUES(?,?);";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

        String SQLmAX = "select max(model_id) FROM cars.model;";
        Statement statement = connection.createStatement();
        ResultSet maxRs = statement.executeQuery(SQLmAX);

        Integer max = null;
        if (maxRs.next()) {
            max = maxRs.getInt(1);
        }


        for(Car car: model.getCars()){
            preparedStatement2.setInt(1, car.getCarId());
            preparedStatement2.setInt(2,max);

            preparedStatement2.addBatch();
            preparedStatement2.execute();

        }

        preparedStatement2.close();

    }

    @Override
    public void insertAModelListIntoModeTable(List<Model> modelList) {
        modelList.forEach(model -> {
            try {
                insertAModelIntoModelTable(model);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Car> carListFromModel(Integer modelID) throws SQLException {
        List<Car> output = new ArrayList<>();

        String sql = "SELECT * FROM cars.car where car_id in (select car_id from cars.model_car where model_id = " + modelID + " );";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            output.add(new Car(resultSet.getInt("car_id"), resultSet.getString("car_name"), resultSet.getString("car_description"), CarType.valueOf(resultSet.getString("car_type"))));
        }

        resultSet.close();
        statement.close();

        return output;
    }

    @Override
    public List<Model> allModels() throws SQLException {
        List<Model> output = new ArrayList<>();

        String sql = "SELECT * FROM cars.model;";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            output.add(new Model(resultSet.getInt("model_id"), resultSet.getString("model_name"), resultSet.getInt("model_year"), carListFromModel(resultSet.getInt("model_id"))));
        }

        resultSet.close();
        statement.close();
        return output;
    }

    @Override
    public void insertAManufacturerIntoManifacturerTable(Manufacturer manufacturer) throws SQLException {
        String sql = "INSERT INTO cars.manufacturer( manufacturer_name, manufacturer_year) VALUES(?,?);";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, manufacturer.getName() );
        preparedStatement.setInt(2, manufacturer.getYearOfCreation() );

        preparedStatement.addBatch();
        preparedStatement.execute();
        preparedStatement.close();

        String sql2 = "Insert into cars.manufacturer_model(manufacturer_id, model_id) Values (?,?);";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);

        String SQLmAX = "select max(manufacturer_id) FROM cars.manufacturer;";
        Statement statement = connection.createStatement();
        ResultSet maxRs = statement.executeQuery(SQLmAX);

        Integer max = null;
        if (maxRs.next()) {
            max = maxRs.getInt(1);
        }

        for (Model model:manufacturer.getModels()){

            preparedStatement2.setInt(1, max);
            preparedStatement2.setInt(2,model.getModelId());

            preparedStatement2.addBatch();
            preparedStatement2.execute();
        }
        preparedStatement2.close();
    }

    @Override
    public void insertAManufacturerListIntoManifacturerTable(List<Manufacturer> manufacturerList) {
        manufacturerList.forEach(manufacturer -> {
            try {
                insertAManufacturerIntoManifacturerTable(manufacturer);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public List<Model> modelListFromManufacturer(Integer id) throws SQLException {
        List<Model> output = new ArrayList<>();

        String sql = "SELECT * FROM cars.model WHERE model_id IN (SELECT model_id FROM cars.manufacturer_model where manufacturer_id =" + id +");";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            output.add(new Model(resultSet.getInt("model_id"), resultSet.getString("model_name"), resultSet.getInt("model_year"),carListFromModel(resultSet.getInt("model_id"))));
        }

        resultSet.close();
        statement.close();

        return output;
    }

    @Override
    public List<Manufacturer> allManufacturer() throws SQLException {
        List<Manufacturer> output = new ArrayList<>();
        String sql ="SELECT * FROM cars.manufacturer;";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            output.add(new Manufacturer(resultSet.getInt("manufacturer_id"), resultSet.getString("manufacturer_name"), resultSet.getInt("manufacturer_year"),modelListFromManufacturer(resultSet.getInt("manufacturer_id"))));
        }

        resultSet.close();
        statement.close();
        return output;
    }

    @Override
    public List<String> allManufacturerNames() throws SQLException {
        return allManufacturer().stream().map(manufacturer -> manufacturer.getName()).collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> allManufacturersEstablishmentYears() throws SQLException {
        return allManufacturer().stream().collect(Collectors.toMap(manufacturer -> manufacturer.getName(),manufacturer -> manufacturer.getYearOfCreation()));
    }

    @Override
    public List<String> allModelNames() throws SQLException {
        return allModels().stream().map(Model::getName).collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> allYearsOfStartingProductionOfModels() throws SQLException {

        return allModels().stream().collect(Collectors.toMap(Model::getName, Model::getProductionStartYear));
    }

    @Override
    public List<Car> carsOfSEDANTypeFromAModelNewerThan2019AndTheManufacturersFoundingYearLessThan1919() throws SQLException {
        return allManufacturer().stream().filter(manufacturer -> manufacturer.getYearOfCreation() < 1919)
                .map(Manufacturer::getModels)
                .flatMap(models -> models.stream())
                .filter(model -> model.getProductionStartYear() > 2019)
                .map(Model::getCars)
                .flatMap(carList -> carList.stream())
                .filter(car -> car.getCarType().equals(CarType.SEDAN))
                .collect(Collectors.toList());
    }


}
