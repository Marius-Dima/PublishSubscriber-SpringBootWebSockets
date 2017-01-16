package computer.networks.controller;

import computer.networks.model.Channel;
import computer.networks.model.Message;
import computer.networks.model.News;
import computer.networks.service.ProfanityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
public class NewsController {

    private static Set<Channel> channelList = new HashSet<Channel>() {{
        add(new Channel("PROTV", "Best news"));
        add(new Channel("PRIMA", "Focus news"));
        add(new Channel("ATOMIC", "Music news"));
    }};
    @Autowired
    private ProfanityFilter profanityFilter;

    @MessageMapping("/{channel}")
    public Message publishNewsToChannel(@DestinationVariable String channel, News news) throws Exception {
        final Boolean blockMsg = profanityFilter.checkMessage(news.getContent());
        if (blockMsg)
            throw new RuntimeException("Message was not published because it contains profanities!");

        return new Message(news.getContent() + " was published to " + channel + "!");
    }

    @RequestMapping(value = "/api/channels", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Channel>> getChannelList() {

        return new ResponseEntity<>(channelList, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/channels/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> getChannelList(@PathVariable("id") Integer channelId) {
        final Optional<Channel> existingChannel = channelList.stream()
                .filter(item -> item.getId().equals(channelId)).findFirst();
        if (!existingChannel.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(existingChannel.get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/channels", method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> addChannel(@RequestBody Channel channel) {
        if (channel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (channelList.contains(channel))
            throw new RuntimeException("Channel " + channel.getName() + " is already present!");

        channelList.add(channel);
        return new ResponseEntity<>(channel, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/channels/{name}", method = RequestMethod.PUT,
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> updateChannel(@PathVariable("name") String channelName, @RequestBody Channel channel) {
        if (channelList.contains(channel))
            throw new RuntimeException("Channel " + channel.getName() + " is already present!");

        final Optional<Channel> existingChannel = channelList.stream()
                .filter(item -> item.getName().equals(channelName)).findFirst();
        if (!existingChannel.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        final Channel updatedChannel = existingChannel.get();
        updatedChannel.updateChannel(channel);
        return new ResponseEntity<>(updatedChannel, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/channels/{name}", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteChannelByName(@PathVariable("name") String channelName) {
        final Optional<Channel> existingChannel = channelList.stream()
                .filter(item -> item.getName().equals(channelName)).findFirst();
        if (!existingChannel.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        channelList.remove(existingChannel.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
