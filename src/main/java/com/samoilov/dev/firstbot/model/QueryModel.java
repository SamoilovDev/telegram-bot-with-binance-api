package com.samoilov.dev.firstbot.model;

import com.samoilov.dev.firstbot.entity.telegram.UserEntity;
import com.samoilov.dev.firstbot.enums.StepType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryModel {

    private Long chatId;

    private UserEntity user;

    private String text;

    private boolean privateChatWithQueryAuthor;

    private StepType stepType;

    public static QueryModel ofCommand(Update update, UserEntity userEntity) {
        return QueryModel.builder()
                .chatId(update.getMessage().getChatId())
                .user(userEntity)
                .text(update.getMessage().getText())
                .privateChatWithQueryAuthor(isPrivateChatWithQueryAuthor(update.getMessage()))
                .stepType(StepType.COMMAND)
                .build();
    }

    public static QueryModel ofCallbackQuery(Update update, UserEntity userEntity) {
        return QueryModel.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .user(userEntity)
                .text(update.getCallbackQuery().getData())
                .privateChatWithQueryAuthor(isPrivateChatWithQueryAuthor(update.getCallbackQuery().getMessage()))
                .stepType(StepType.CALLBACK)
                .build();
    }

    public String getCommand() {
        return Arrays.stream(this.text.split(" "))
                .filter(e -> e.matches("^/\\D+$"))
                .collect(Collectors.joining());
    }

    public StepType defineStep() {
        assert this.text != null;
        try {
            String step = this.text.split("\\s+")[1];
            return Arrays.stream(StepType.values())
                    .filter(v -> v.name().equals(step))
                    .findFirst()
                    .orElse(StepType.START);
        } catch (ArrayIndexOutOfBoundsException e) {
            return StepType.START;
        }
    }

    private static boolean isPrivateChatWithQueryAuthor(Message message) {
        return message.getChat().isUserChat()
                && message.getChat().getFirstName().equals(message.getFrom().getFirstName());
    }

}
