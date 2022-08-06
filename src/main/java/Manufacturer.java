import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Manufacturer {
    private Integer manufacturerId;
    private String name;
    private Integer yearOfCreation;
    private List<Model> models;

    public Manufacturer(String name, Integer yearOfCreation, List<Model> models) {
        this.name = name;
        this.yearOfCreation = yearOfCreation;
        this.models = models;
    }
}

