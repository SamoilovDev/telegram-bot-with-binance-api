package com.samoilov.dev.firstbot.service;

import com.samoilov.dev.firstbot.entity.telegram.UserEntity;
import com.samoilov.dev.firstbot.service.domain.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@AllArgsConstructor
public class CurrencyExchangeBotService {

    @Autowired
    private final UserService userService;

    public SendMessage startMessage(Update update) {
        UserEntity user = userService.findOrSave(update.getMessage().getFrom());

        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Привет, уважаемый(-ая) %s %s!".formatted(user.getFirstName(), user.getLastName()))
                .build();
    }

    public SendMessage errorMessage(Update update) {
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("Прости, я слишком глуп, чтобы понять тебя. Попробуй ввести /help для получения допустимых команд.")
                .build();
    }

}
