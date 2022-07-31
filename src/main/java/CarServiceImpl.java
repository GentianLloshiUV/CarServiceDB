import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
