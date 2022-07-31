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
}

