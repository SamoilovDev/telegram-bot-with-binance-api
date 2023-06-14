package com.samoilov.dev.firstbot.service.domain;

import com.samoilov.dev.firstbot.entity.telegram.UserEntity;
import com.samoilov.dev.firstbot.mapper.UserMapper;
import com.samoilov.dev.firstbot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findOrSave(User user) {
        return userRepository.findByTelegramId(user.getId())
                .orElseGet(() -> this.save(UserMapper.map(user)));
    }

    public void incrementCount(User user) {
        UserEntity userEntity = this.findOrSave(user);
        userEntity.setCommandCounter(userEntity.getCommandCounter() + 1);
        this.save(userEntity);
    }

    private UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }


    public boolean deleteByTelegramId(Long telegramId) {
        return userRepository.deleteByTelegramId(telegramId) > 0;
    }

}
