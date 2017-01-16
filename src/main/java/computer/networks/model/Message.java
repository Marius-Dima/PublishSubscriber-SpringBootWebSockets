package computer.networks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Message extends TransactionalEntity {
    private static final long serialVersionUID = 1L;
    private String content;
}
