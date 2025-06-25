package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.register.NewPassword;
import ru.skypro.homework.dto.register.UpdateUser;
import ru.skypro.homework.dto.user.User;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public boolean updatePassword(NewPassword newPassword) {
        return true;
    }

    @Override
    public User getInfoAboutUser() {
        return null;
    }

    @Override
    public UpdateUser updateInfoAboutUser(UpdateUser newInfoUser) {
        return null;
    }

    @Override
    public boolean updateAvatarUser(String image) {
        return true;
    }
}
