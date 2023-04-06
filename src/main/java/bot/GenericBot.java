package bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bot.interfaces.Dispatcher;

@Service(value = "Bot")
public class GenericBot extends TelegramLongPollingBot implements Dispatcher {

	private final static Logger LOG = LogManager.getLogger(GenericBot.class);

	@Value("${bot.username}")
	private String botUsername;
	@Value("${bot.apikey}")
	private String botApiKey;
	@Autowired
	private ApplicationContext appContext;

	public GenericBot() {
		LOG.info("Bot started!");
	}

	@Override
	public synchronized void onUpdateReceived(Update update) {
		LOG.debug(update);

		try {
			appContext.getBeansOfType(Processor.class).values().forEach(e -> e.onUpdateReceived(update));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	public String getBotToken() {
		return botApiKey;
	}

	@Override
	public void pushMessage(String text, Long chatId) {
		try {
			SendMessage msg = new SendMessage(String.valueOf(chatId), text);
			msg.setParseMode("HTML");
			execute(msg).getMessageId();
		} catch (TelegramApiException e) {
			LOG.error(e.getMessage(), e);

		}
	}
}
