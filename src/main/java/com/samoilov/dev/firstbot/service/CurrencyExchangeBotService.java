package com.samoilov.dev.firstbot.service;

import com.samoilov.dev.firstbot.config.properties.BinanceProperties;
import com.samoilov.dev.firstbot.entity.telegram.UserEntity;
import com.samoilov.dev.firstbot.enums.MessageType;
import com.samoilov.dev.firstbot.service.domain.BinanceCodeService;
import com.samoilov.dev.firstbot.service.domain.BinancePriceService;
import com.samoilov.dev.firstbot.service.domain.UserService;
import com.samoilov.dev.firstbot.service.util.Buttons;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Objects;

import static com.samoilov.dev.firstbot.service.util.Messages.*;

@Slf4j
@Service
@AllArgsConstructor
public class CurrencyExchangeBotService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final BinanceProperties binanceProperties;

    @Autowired
    private final BinanceCodeService binanceCodeService;

    @Autowired
    private final BinancePriceService binancePriceService;

    public SendMessage getResponse(Update update, MessageType type) {
        String chatId = String.valueOf(
                update.hasMessage()
                        ? update.getMessage().getChatId()
                        : update.getCallbackQuery().getMessage().getChatId()
        );

        userService.incrementCount(
                update.hasMessage()
                        ? update.getMessage().getFrom()
                        : update.getCallbackQuery().getFrom()
        );

        return switch (type) {
            case START -> this.getStartMessage(update, chatId);
            case INFO -> this.getInfoMessage(chatId);
            case HELP -> this.getHelpMessage(chatId);
            case BINANCE -> this.getBinanceMessage(chatId);
            case RATE -> this.getLastRate(update, chatId);
            case ERROR -> this.getErrorMessage(chatId);
        };
    }



    public SendMessage getLastRate(Update update, String chatId) {
        String[] command = update.hasMessage()
                ? update.getMessage().getText().split("\\s+")
                : update.getCallbackQuery().getData().split("\\s+");
        String message = command.length < 2
                ? "Выберите один из предложенных валютных пар (или повторно введите команду со своей парой): "
                : "Последнee значение %s: ".formatted(command[1]).concat(
                        String.valueOf(
                                binancePriceService.getLastPrice(binanceCodeService.get(command[1]))
                                        .getValue()
                                        .doubleValue() / binanceProperties.getMultiplyValue().doubleValue()
                        )
        );

        log.info("Sent rate message to user with id {}", chatId);

        return SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .replyMarkup(
                        command.length < 2
                                ? Buttons.getDefaultRateButtons()
                                : Buttons.getButtonsByCommands(List.of("/help", "/binance"))
                )
                .build();
    }

    private SendMessage getBinanceMessage(String chatId) {
        log.info("Sent binance message to user with id {}", chatId);
        return SendMessage.builder()
                .chatId(chatId)
                .text(BINANCE_MESSAGE)
                .replyMarkup(Buttons.getButtonsByCommands(List.of("/help", "/binance")))
                .build();
    }

    private SendMessage getHelpMessage(String chatId) {
        log.info("Sent help message to user with id {}", chatId);
        return SendMessage.builder()
                .chatId(chatId)
                .text(HELP_MESSAGE)
                .replyMarkup(Buttons.getAllButtons())
                .build();
    }

    private SendMessage getInfoMessage(String chatId) {
        log.info("Sent info message to user with id {}", chatId);
        return SendMessage.builder()
                .chatId(chatId)
                .text(INFORMATION_MESSAGE)
                .replyMarkup(Buttons.getButtonsByCommands(List.of("/help", "/binance")))
                .build();
    }

    private SendMessage getStartMessage(Update update, String chatId) {
        UserEntity user = update.hasMessage()
                ? userService.findOrSave(update.getMessage().getFrom())
                : userService.findOrSave(update.getCallbackQuery().getFrom());
        String firstName = Objects.nonNull(user.getFirstName()) ? user.getFirstName() : "";
        String lastname = Objects.nonNull(user.getLastName()) ? user.getLastName() : "";

        log.info("User {} {} with id {} sent start command", firstName, lastname,  user.getTelegramId());

        return SendMessage.builder()
                .chatId(chatId)
                .text(START_MESSAGE.formatted(firstName, lastname))
                .replyMarkup(Buttons.getButtonsByCommands(List.of("/help", "/info")))
                .build();

    }

    private SendMessage getErrorMessage(String chatId) {
        return SendMessage.builder()
                .text(ERROR_MESSAGE)
                .chatId(chatId)
                .replyMarkup(Buttons.getButtonsByCommands(List.of("/help")))
                .build();
    }

}
