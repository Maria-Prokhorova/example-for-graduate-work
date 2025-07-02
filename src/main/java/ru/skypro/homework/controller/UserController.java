package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.register.NewPassword;
import ru.skypro.homework.dto.register.UpdateUser;
import ru.skypro.homework.dto.user.User;
import ru.skypro.homework.service.UserService;

@Tag(name = "Пользователи", description = "Раздел содержит методы по работе личной информацией пользователя")
@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Обновление пароля")
    @PostMapping("/set_password")
    public boolean updatePassword(@RequestBody NewPassword newPassword) {
        return userService.updatePassword(newPassword);
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    public User getInfoAboutUser() {
        return userService.getInfoAboutUser();
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    public UpdateUser updateInfoAboutUser(@RequestBody UpdateUser newInfoUser) {
        return userService.updateInfoAboutUser(newInfoUser);
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping(value = "me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateAvatarUser(@RequestParam MultipartFile image) {
        userService.updateAvatarUser(image);
    }
}
