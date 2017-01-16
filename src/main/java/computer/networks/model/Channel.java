package computer.networks.model;


import java.util.concurrent.atomic.AtomicInteger;

public class Channel {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private Integer id;
    private String name;
    private String description;

    public Channel() {
        this.id = counter.incrementAndGet();
    }

    public Channel(String name, String description) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
