package computer.networks.controller;

import computer.networks.model.Channel;
import computer.networks.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value = "api")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "channels", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Channel>> getChannels() {
        return new ResponseEntity<>(channelService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "channels/{id}", method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> getChannel(@PathVariable("id") Long channelId) {
        final Channel channel = channelService.findOne(channelId);
        return new ResponseEntity<>(channel, HttpStatus.OK);
    }

    @RequestMapping(value = "channels", method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> addChannel(@RequestBody Channel channel) {
        if (channel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        channelService.save(channel);
        return new ResponseEntity<>(channel, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/channels/{id}", method = RequestMethod.PUT,
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> updateChannel(@PathVariable("id") Long id, @RequestBody Channel channel) {
        final Channel updatedChannel = channelService.update(channel);

        if (updatedChannel == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(updatedChannel, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/channels/{id}", method = RequestMethod.DELETE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteChannelByName(@PathVariable("id") Long channelId) {
        channelService.delete(channelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
