package bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Main {

	private final static Logger LOG = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext context) {
		return args -> {
			try {
				TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
				telegramBotsApi.registerBot((GenericBot) context.getBean("Bot"));
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		};
	}

}