package com.samoilov.dev.firstbot.config;

import com.samoilov.dev.firstbot.controller.CurrencyExchangeBotController;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@AllArgsConstructor
public class TelegramConfiguration {

    private final CurrencyExchangeBotController currencyExchangeBotApi;

    @EventListener({ContextRefreshedEvent.class})
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(currencyExchangeBotApi);
        return api;
    }

}
