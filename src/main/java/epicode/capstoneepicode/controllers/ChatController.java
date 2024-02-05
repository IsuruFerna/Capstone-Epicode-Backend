package epicode.capstoneepicode.controllers;

import epicode.capstoneepicode.entities.Greeting;
import epicode.capstoneepicode.entities.HelloMessage;
import epicode.capstoneepicode.entities.Message;
import epicode.capstoneepicode.entities.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }


//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public Greeting greet(HelloMessage message) {
//        return new Greeting("Hello," +
//                HtmlUtils.htmlEscape(message.getName()));
//    }
}
