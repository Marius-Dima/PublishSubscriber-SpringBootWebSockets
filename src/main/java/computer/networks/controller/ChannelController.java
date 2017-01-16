package computer.networks.controller;

import computer.networks.model.Channel;
import computer.networks.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Controller
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @RequestMapping(value = "/api/channels", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Channel>> getChannelList() {

        return new ResponseEntity<>(channelService.findAll(), HttpStatus.OK);
    }
}
