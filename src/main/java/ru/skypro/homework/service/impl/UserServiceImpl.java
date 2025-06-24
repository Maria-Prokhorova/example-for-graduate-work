package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.register.NewPasswordDto;
import ru.skypro.homework.dto.register.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean updatePassword(NewPasswordDto newPasswordDto) {
        return true;
    }

    @Override
    public UserDto getInfoAboutUser() {
        return null;
    }

    @Override
    public UpdateUserDto updateInfoAboutUser(UpdateUserDto newInfoUser) {
        return null;
    }

    @Override
    public boolean updateAvatarUser(String image) {
        return true;
    }
}
