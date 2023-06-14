package com.samoilov.dev.firstbot.mapper;

import com.samoilov.dev.firstbot.entity.telegram.UserEntity;
import org.telegram.telegrambots.meta.api.objects.User;

public class UserMapper {

    public static UserEntity map(User user) {
        UserEntity chatUser = new UserEntity();
        chatUser.setTelegramId(user.getId());
        chatUser.setUserName(user.getUserName());
        chatUser.setFirstName(user.getFirstName());
        chatUser.setLastName(user.getLastName());
        return chatUser;
    }

    public static User map(UserEntity userEntity) {
        User chatUser = new User();
        chatUser.setId(userEntity.getTelegramId());
        chatUser.setUserName(userEntity.getUserName());
        chatUser.setFirstName(userEntity.getFirstName());
        chatUser.setLastName(userEntity.getLastName());
        return chatUser;
    }

}
