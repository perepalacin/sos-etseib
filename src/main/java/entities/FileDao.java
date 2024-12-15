package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDao {
    int id;
    int parent_id;
    String name;
    String type;
    String s3_url;
}
