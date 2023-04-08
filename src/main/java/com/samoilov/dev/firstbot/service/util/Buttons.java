package com.samoilov.dev.firstbot.service.util;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Buttons {

    private static final Map<String, InlineKeyboardButton> DEFAULT_RATE_BUTTONS = Map.of(
            "USTBTC", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("\uD83D\uDCB5USDT to ₿TC")).callbackData("/rate USTBTC").build(),
            "BTCUST", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("₿TC to \uD83D\uDCB5USDT")).callbackData("/rate BTCUST").build(),
            "EURUSDT", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("\uD83D\uDCB6EUR to \uD83D\uDCB5USDT")).callbackData("/rate EURUSDT").build(),
            "BTCEUR", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("₿TC to \uD83D\uDCB6EUR")).callbackData("/rate BTCEUR").build()

    );

    private static final Map<String, InlineKeyboardButton> ALL_BUTTONS = Map.of(
            "/start", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("\uD83D\uDE80 Начало")).callbackData("/start").build(),
            "/help", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("\uD83D\uDCA1 Комманды")).callbackData("/help").build(),
            "/info", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("ℹ Oбо мне")).callbackData("/info").build(),
            "/rate", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("\uD83D\uDCB1 Конвертер")).callbackData("/rate").build(),
            "/binance", InlineKeyboardButton.builder().text(EmojiParser.parseToUnicode("\uD83D\uDCB9 Binance")).url("https://www.binance.com/ru").build()
    );

    public static InlineKeyboardMarkup getDefaultRateButtons() {
        List<List<InlineKeyboardButton>> trademarksButtons = new ArrayList<>(DEFAULT_RATE_BUTTONS.values().stream()
                .map(List::of)
                .toList());

        trademarksButtons.add(List.of(
                ALL_BUTTONS.get("/help"),
                ALL_BUTTONS.get("/binance")
        ));

        return new InlineKeyboardMarkup(trademarksButtons);
    }

    public static InlineKeyboardMarkup getAllButtons() {
        List<InlineKeyboardButton> allButtons = ALL_BUTTONS.values().stream().toList();
        return new InlineKeyboardMarkup(
                List.of(
                        allButtons.subList(0, allButtons.size() / 2),
                        allButtons.subList(allButtons.size() / 2, allButtons.size())
                )
        );
    }

    public static InlineKeyboardMarkup getButtonsByCommands(List<String> commands) {
        List<InlineKeyboardButton> foundedButtons = commands.stream()
                .map(ALL_BUTTONS::get)
                .toList();
        return new InlineKeyboardMarkup(
                List.of(
                        foundedButtons.subList(0, foundedButtons.size() / 2),
                        foundedButtons.subList(foundedButtons.size() / 2, foundedButtons.size())
                )
        );
    }

}
