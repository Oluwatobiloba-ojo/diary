package gossipVille.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class CreateEntryRequest {
    private String username;
    private String body;
    private String title;
}
