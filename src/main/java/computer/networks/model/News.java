package computer.networks.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
public class News extends TransactionalEntity {
    private static final long serialVersionUID = 1L;
    private String content;
}
