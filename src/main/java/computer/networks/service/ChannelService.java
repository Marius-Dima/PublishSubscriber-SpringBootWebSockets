package computer.networks.service;

import computer.networks.model.Channel;

import java.util.Collection;
import java.util.List;

public interface ChannelService {

    Collection<Channel> findAll();

    Channel findOne(Long channelId);

    Channel findByName(String name);

    List<Channel> saveAll(Iterable<Channel> channels);

    Channel save(Channel channel);

    Channel update(Channel channel);

    void delete(Long id);

    void evictCache();

}
