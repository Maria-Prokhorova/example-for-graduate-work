package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.register.NewPasswordDto;
import ru.skypro.homework.dto.register.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
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
    public boolean updatePassword(@RequestBody NewPasswordDto newPasswordDto) {
        return userService.updatePassword(newPasswordDto);
    }

    @Operation(summary = "Получение информации об авторизованном пользователе")
    @GetMapping("/me")
    public UserDto getInfoAboutUser() {
        return userService.getInfoAboutUser();
    }

    @Operation(summary = "Обновление информации об авторизованном пользователе")
    @PatchMapping("/me")
    public UpdateUserDto updateInfoAboutUser(@RequestBody UpdateUserDto newInfoUser) {
        return userService.updateInfoAboutUser(newInfoUser);
    }

    @Operation(summary = "Обновление аватара авторизованного пользователя")
    @PatchMapping("me/image")
    public boolean updateAvatarUser(@RequestBody String image) {
        return userService.updateAvatarUser(image);
    }
}
