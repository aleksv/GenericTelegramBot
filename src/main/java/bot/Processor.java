package bot;

import java.util.Optional;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Processor {

	private Update update;
	private final String prefix;

	public abstract String getDescription();

	public abstract void onUpdateReceived();

	public Processor(String prefix) {
		this.prefix = prefix;
	}

	public synchronized final void onUpdateReceived(Update update) {
		this.update = update;
		onUpdateReceived();
	}

	public boolean messageMatches(String regex) {
		return Optional.of(update).map(Update::getMessage).map(Message::getText).map(String::toLowerCase)
				.map(t -> t.matches("/" + getPrefix() + (regex.length() > 0 ? " " : "") + regex)).orElse(false);
	}

	public long getChatId() {
		return Optional.of(update).map(Update::getMessage).map(Message::getChatId).orElseThrow();
	}

	public Update getUpdate() {
		return update;
	}

	public final String getPrefix() {
		return prefix;
	}
}