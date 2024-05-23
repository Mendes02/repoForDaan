package fontys.sem3.school.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetVideoGameSearchFilterRequest {
    String genre;
    String name;
}
