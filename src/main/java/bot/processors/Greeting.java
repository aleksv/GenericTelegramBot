package bot.processors;

import org.springframework.stereotype.Component;

import bot.Processor;
import bot.interfaces.Dispatcher;

@Component
public class Greeting extends Processor {

    private Dispatcher dispatcher;

    public Greeting(Dispatcher dispatcher) {
        super("greeting");
        this.dispatcher = dispatcher;
    }

    @Override
    public String getDescription() {
        return "/greeting hi - say hi\n"
                + "/greeting bye -say bye";
    }

    @Override
    public void onUpdateReceived() {
        if (messageMatches("hi")) {
            dispatcher.pushMessage("hi", getChatId());
        } else if (messageMatches("bye")) {
            dispatcher.pushMessage("bye", getChatId());
        }
    }

}
