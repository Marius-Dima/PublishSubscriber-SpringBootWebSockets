package computer.networks;

import computer.networks.filter.RequestContext;
import computer.networks.model.Channel;
import computer.networks.service.ChannelService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        return new GuavaCacheManager("channel");
    }

    @Bean
    CommandLineRunner init(final ChannelService channelService) {
        return args -> {
            RequestContext.init();
            final HashSet<Channel> channels = new HashSet<>();
            channels.add(new Channel("PROTV", "Best news"));
            channels.add(new Channel("PRIMA", "Focus news"));
            channels.add(new Channel("ATOMIC", "Atomic news"));

            channelService.saveAll(channels);
        };
    }

}
