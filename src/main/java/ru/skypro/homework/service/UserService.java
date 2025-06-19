package ru.skypro.homework.service;

import ru.skypro.homework.dto.register.NewPassword;
import ru.skypro.homework.dto.register.UpdateUser;
import ru.skypro.homework.dto.user.User;

public interface UserService {

    boolean updatePassword(NewPassword newPassword);

    User getInfoAboutUser();

    UpdateUser updateInfoAboutUser(UpdateUser newInfoUser);

    boolean updateAvatarUser(String image);
}
