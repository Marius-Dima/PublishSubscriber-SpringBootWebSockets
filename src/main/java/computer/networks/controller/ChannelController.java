package computer.networks.controller;

import computer.networks.model.Channel;
import computer.networks.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(value = "api")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @GetMapping(value = "channels")
    public ResponseEntity<Collection<Channel>> getChannels() {
        return new ResponseEntity<>(channelService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "channels/{id}")
    public ResponseEntity<Channel> getChannel(@PathVariable("id") Long channelId) {
        final Channel channel = channelService.findOne(channelId);
        return new ResponseEntity<>(channel, HttpStatus.OK);
    }

    @PostMapping(value = "channels")
    public ResponseEntity<Channel> addChannel(@RequestBody Channel channel) {
        if (channel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        channelService.save(channel);
        return new ResponseEntity<>(channel, HttpStatus.CREATED);
    }

    @PutMapping(value = "channels/{id}")
    public ResponseEntity<Channel> updateChannel(@PathVariable("id") Long id, @RequestBody Channel channel) {
        final Channel updatedChannel = channelService.update(channel);

        if (updatedChannel == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(updatedChannel, HttpStatus.OK);
    }

    @DeleteMapping(value = "channels/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) {
        channelService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "channels")
    public ResponseEntity delete(@RequestBody Channel channel) {
        if (channel == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        final Channel foundChannel = channelService.findByName(channel.getName());

        if (foundChannel != null)
            channelService.delete(foundChannel.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
