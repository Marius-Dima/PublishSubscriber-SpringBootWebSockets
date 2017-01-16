package computer.networks.service;

import computer.networks.model.Channel;
import computer.networks.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public Collection<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    @Cacheable(value = "channel", key = "#channelId")
    public Channel findOne(Long channelId) {
        return channelRepository.findOne(channelId);
    }

    @Override
    @Cacheable(value = "channel", key = "#channelName")
    public Channel findByName(String channelName) {
        return channelRepository.findByName(channelName);
    }

    @Override
    public List<Channel> saveAll(Iterable<Channel> channels) {
        return channelRepository.save(channels);
    }

    @Override
    @CachePut(value = "channel", key = "#channel.id")
    public Channel save(Channel channel) {
        if (channel.getId() != null)
            return null;
        return channelRepository.save(channel);
    }

    @Override
    @CachePut(value = "channel", key = "#channel.id")
    public Channel update(Channel channel) {
        final Channel persistedChannel = channelRepository.findOne(channel.getId());

        if (persistedChannel == null)
            return null;

        persistedChannel.updateChannel(channel);
        return channelRepository.save(persistedChannel);
    }

    @Override
    @CacheEvict(value = "channel", key = "#id")
    public void delete(Long id) {
        channelRepository.delete(id);
    }

    @Override
    @CacheEvict(value = "channel", allEntries = true)
    public void evictCache() {

    }
}
