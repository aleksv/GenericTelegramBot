package bot.processors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import bot.Processor;
import bot.interfaces.Dispatcher;

@Component
public class Help extends Processor {

    private Dispatcher dispatcher;
    private List<Processor> processors;

    public Help(Dispatcher dispatcher, List<Processor> processors) {
        super("help");
        this.dispatcher = dispatcher;
        this.processors = processors;
    }

    @Override
    public void onUpdateReceived() {
        if (messageMatches("[a-z0-9]+")) {
            dispatcher.pushMessage(
                    processors.stream()
                            .filter(e -> getUpdate().getMessage().getText().toLowerCase()
                                    .contains(e.getPrefix().toLowerCase()))
                            .findFirst().map(e -> e.getDescription()).orElse(""),
                    getChatId());
        } else if (isHelp()) {
            dispatcher.pushMessage("Choose /help 'name':\n\n" +
                    processors.stream().filter(e -> e.getDescription() != null).map(Processor::getPrefix).sorted()
                            .collect(Collectors.joining("\n")),
                    getChatId());
        }
    }

    private boolean isHelp() {
        return messageMatches("");
    }

    @Override
    public String getDescription() {
        return null; // no need for a help text
    }

}