package com.samoilov.dev.firstbot.controller;

import com.samoilov.dev.firstbot.config.properties.TelegramProperties;
import com.samoilov.dev.firstbot.service.CurrencyExchangeBotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;

@Slf4j
@Component
@AllArgsConstructor
public class CurrencyExchangeBotController extends TelegramLongPollingBot {

    @Autowired
    private final TelegramProperties telegramProperties;

    @Autowired
    private final CurrencyExchangeBotService currencyExchangeBotService;

    @Override
    public String getBotUsername() {
        return telegramProperties.getName();
    }

    @Override
    public String getBotToken() {
        return telegramProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.hasMessage() && update.getMessage().hasText()
                ? update.getMessage().getText()
                : EMPTY_STRING;

        SendMessage sendMessage = message.isBlank()
                ? null
                : switch (message.split("\\s+")[0]) {
            case "/start" -> currencyExchangeBotService.startMessage(update);
            default -> currencyExchangeBotService.errorMessage(update);
        };

        try {
            super.execute(sendMessage);
            log.info("Message was sent: ".concat(Objects.requireNonNull(sendMessage).getText()));
        } catch (TelegramApiException e) {
            log.warn(e.getMessage());
        }
    }

}
