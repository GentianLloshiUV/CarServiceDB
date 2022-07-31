import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Car {
    private Integer carId;
    private String name;
    private String description;
    private CarType carType;

    public Car(String name, String description, CarType carType) {
        this.name = name;
        this.description = description;
        this.carType = carType;
    }
}

