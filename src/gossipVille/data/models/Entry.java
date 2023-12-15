package gossipVille.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document
@Data
public class Entry {
    @Id
    private String id;
    private String title;
    private String body;
    private LocalDateTime localDateTime;
    private String diaryId;
}
