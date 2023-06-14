package com.samoilov.dev.firstbot.controller;

import com.samoilov.dev.firstbot.config.properties.TelegramProperties;
import com.samoilov.dev.firstbot.enums.CommandType;
import com.samoilov.dev.firstbot.enums.MessageType;
import com.samoilov.dev.firstbot.service.CurrencyExchangeBotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Objects;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static com.samoilov.dev.firstbot.enums.MessageType.*;

@Slf4j
@Component
@AllArgsConstructor
public class CurrencyExchangeBotController extends TelegramLongPollingBot {

    private final TelegramProperties telegramProperties;

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
        String message = EMPTY_STRING;
        if (update.hasMessage()) {
            message = update.getMessage().hasText()
                    ? update.getMessage().getText()
                    : EMPTY_STRING;
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getData();
        }


        MessageType messageType = switch (message.split("\\s+")[0]) {
            case CommandType.START -> START;
            case CommandType.INFO -> INFO;
            case CommandType.HELP -> HELP;
            case CommandType.BINANCE -> BINANCE;
            case CommandType.RATE -> RATE;
            default -> ERROR;
        };
        SendMessage messageToSend = currencyExchangeBotService.getResponse(update, messageType);

        try {
            super.execute(messageToSend);
            log.info("Message was sent: ".concat(Objects.requireNonNull(messageToSend).getText()));
        } catch (TelegramApiException e) {
            log.warn(e.getMessage());
        }
    }

}
