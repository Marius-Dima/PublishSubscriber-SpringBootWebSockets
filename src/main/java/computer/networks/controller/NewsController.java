package computer.networks.controller;

import computer.networks.model.Message;
import computer.networks.model.News;
import computer.networks.service.ProfanityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class NewsController {

    @Autowired
    private ProfanityFilter profanityFilter;

    @MessageMapping("/{channel}")
    public Message publishNewsToChannel(@DestinationVariable String channel, News news) throws Exception {
        final Boolean blockMsg = profanityFilter.checkMessage(news.getContent());
        if (blockMsg)
            throw new RuntimeException("Message was not published because it contains profanities!");

        return new Message(news.getContent() + " was published to " + channel + "!");
    }

}
