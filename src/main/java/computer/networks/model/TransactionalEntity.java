package computer.networks.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import computer.networks.filter.RequestContext;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
public class TransactionalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String referenceId = UUID.randomUUID().toString();

    @Version
    private Integer version;

    @NotNull
    private String createdBy;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private DateTime createdAt;

    private String updatedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private DateTime updatedAt;

    @PrePersist
    public void beforePersist() {
        String username = RequestContext.getUsername();
        if (username == null) {
            throw new IllegalArgumentException(
                    "Cannot persist a TransactionalEntity without a username "
                            + "in the RequestContext for this thread.");
        }
        setCreatedBy(username);

        setCreatedAt(new DateTime());
    }

    @PreUpdate
    public void beforeUpdate() {
        String username = RequestContext.getUsername();
        if (username == null) {
            throw new IllegalArgumentException(
                    "Cannot update a TransactionalEntity without a username "
                            + "in the RequestContext for this thread.");
        }
        setUpdatedBy(username);

        setUpdatedAt(new DateTime());
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) {
            return false;
        }
        if (this.getClass().equals(that.getClass())) {
            TransactionalEntity thatTE = (TransactionalEntity) that;
            if (this.getId() == null || thatTE.getId() == null) {
                return false;
            }
            if (this.getId().equals(thatTE.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return -1;
        }
        return getId().hashCode();
    }
}
