package ru.skypro.homework.service;

import ru.skypro.homework.dto.register.NewPasswordDto;
import ru.skypro.homework.dto.register.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;

public interface UserService {

    boolean updatePassword(NewPasswordDto newPasswordDto);

    UserDto getInfoAboutUser();

    UpdateUserDto updateInfoAboutUser(UpdateUserDto newInfoUser);

    boolean updateAvatarUser(String image);
}
