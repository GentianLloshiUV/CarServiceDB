import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Model {
    private Integer modelId;
    private String name;
    private Integer productionStartYear;
    private List<Car> cars;
}

