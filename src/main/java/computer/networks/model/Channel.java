package computer.networks.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
public class Channel extends TransactionalEntity {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public Channel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateChannel(Channel channel) {
        this.name = channel.getName();
        this.description = channel.getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Channel channel = (Channel) o;

        return (name != null ? name.equals(channel.name) : channel.name == null) && (description != null ? description.equals(channel.description) : channel.description == null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
