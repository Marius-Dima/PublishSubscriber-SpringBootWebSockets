package computer.networks.service;

import computer.networks.model.Channel;

import java.util.Collection;

public interface ChannelService {

    Collection<Channel> findAll();

    Channel findOne(Long channelId);

    Channel findByName(String name);

    Channel save(Channel channel);

    Channel update(Channel channel);

    void delete(Long id);

    void evictCache();

}
